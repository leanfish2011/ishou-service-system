package com.tim.ishou.system.job;

import com.tim.ishou.site.feign.SiteFeignClient;
import com.tim.ishou.site.vo.SiteHomeSearchData;
import com.tim.ishou.site.vo.SiteHomeSearchResp;
import com.tim.ishou.system.component.MailHandle;
import com.tim.ishou.system.po.SiteSub;
import com.tim.ishou.system.service.SiteSubService;
import com.tim.message.MainCode;
import com.tim.message.Message;
import com.tim.system.sdk.po.MailReq;
import com.tim.util.DateFormateUtil;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author：tim
 * @date：20-7-4 下午10:20
 * @description：网址发布服务
 */
@Component
@Slf4j
public class SitePubJob {

  @Value("${home.site.address:http://106.53.116.69}")
  private String siteAddress;

  @Autowired
  private MailHandle mailHandle;

  @Autowired
  private SiteFeignClient siteFeignClient;

  @Autowired
  private SiteSubService siteSubService;

  @Value("${home.site.pub.switch:true}")
  private boolean pubSwitch;

  /**
   * 首页网站发布。每周日上午8点发送订阅邮件
   */
  @Scheduled(cron = "${home.site.pub.cron:0 0 8 ? * 1}")
  //@Scheduled(fixedRate = 5000)
  public void homeSitePub() {
    if (!pubSwitch) {
      log.warn("首页网站发布开关关闭！");
      return;
    }

    Message<SiteHomeSearchData> homeSearchDataMessage = siteFeignClient.list();
    if (homeSearchDataMessage.getCode() != MainCode.SUCCESS) {
      log.error("读取主页网址失败：", homeSearchDataMessage.getMsg());
      return;
    }

    SiteHomeSearchData siteHomeSearchData = homeSearchDataMessage.getData();
    if (siteHomeSearchData == null) {
      log.warn("无主页网址内容");
      return;
    }

    String subject = "爱收藏-首页网站备份-" + DateFormateUtil.dateFormat(new Date());
    String fullStyle = getFullStyle();
    String siteTable = getSiteTable(getSiteTableTitle(), getSiteTableList(siteHomeSearchData));
    String subTable = getSubTable();

    List<SiteSub> siteSubList = siteSubService.getSubEmail();
    if (siteSubList == null || siteSubList.size() == 0) {
      log.warn("没有主页网址订阅者！");
    }

    siteSubList.stream().parallel().forEach(siteSub -> {
          String unSubInfo = getUnSubInfo(siteSub.getId());
          String content = getContent(fullStyle, siteTable, subTable, unSubInfo);

          MailReq mailReq = new MailReq(siteSub.getEmail(), subject, content);
          mailHandle.sendMimeMessge(mailReq);
        }
    );

  }

  /**
   * 返回邮件内容
   *
   * @param fullStyle 全局内容样式
   * @param siteTable 订阅的网站内容
   * @param subTable 订阅信息
   * @param unSubInfo 取消订阅链接
   * @return 邮件内容
   */
  private String getContent(String fullStyle, String siteTable, String subTable, String unSubInfo) {
    StringBuilder contentBuilder = new StringBuilder();
    contentBuilder.append("<div id=\"content\" class=\"ishou_mail_readhtml\">");
    contentBuilder.append(fullStyle);
    contentBuilder.append(siteTable);
    contentBuilder.append(subTable);
    contentBuilder.append(unSubInfo);
    contentBuilder.append("</div>");

    return contentBuilder.toString();
  }

  /**
   * 全局样式
   */
  private String getFullStyle() {
    return "<style type=\"text/css\">"
        + "   .ishou_mail_readhtml,a.mycolor:link {"
        + "        color: #3F3F3F;"
        + "        text-decoration: none;"
        + "    }"
        + "    .ishou_mail_readhtml,a.mycolor:visited {"
        + "        color: #3F3F3F;"
        + "        text-decoration: none;"
        + "    }"
        + "    .ishou_mail_readhtml,a.mycolor:hover {"
        + "         color: #00B58A;"
        + "         text-decoration: none;"
        + "    }"
        + "</style>";
  }

  /**
   * 邮件内容主体
   *
   * @param title 内容标题
   * @param siteList 内容网站列表
   * @return 邮件内容
   */
  private String getSiteTable(String title, String siteList) {
    StringBuilder siteTableBuilder = new StringBuilder();
    siteTableBuilder.append(
        "<table width=\"700px\" style=\"margin-left: auto; margin-right: auto; padding-top: 60px;\" cellspacing=\"0\" cellpadding=\"0\">");
    siteTableBuilder.append("<tbody>");
    siteTableBuilder.append(title);
    siteTableBuilder.append(siteList);
    siteTableBuilder.append("</tbody>");
    siteTableBuilder.append("</table>");

    return siteTableBuilder.toString();
  }

  /**
   * 邮件内容标题
   */
  private String getSiteTableTitle() {
    return "<tr>"
        + "  <td>"
        + "    <table width=\"700px\" bgcolor=\"#ffffff\" height=\"127px\" style=\"margin-left: auto; margin-right: auto; padding-left: 30px\" cellspacing=\"0\" cellpadding=\"0\">"
        + "     <tbody>"
        + "      <tr>"
        + "        <td width=\"493px\">"
        + "         <table>"
        + "          <tbody>"
        + "            <tr height=\"60px\">"
        + "             <td><a href=\"" + siteAddress + "\" target=\"_blank\">"
        + "                   <img height=\"50\" width=\"50\" src=\"" + siteAddress
        + "/static/ishou.ico\" /></a>"
        + "                 <span style=\"font-size: 20pt;\">&nbsp;爱收藏</span><b style=\"font-size: 22pt;\">「每周经典网站」</b> "
        + "             </td>"
        + "            </tr>"
        + "          </tbody>"
        + "         </table>"
        + "       </td>"
        + "       <td style=\"text-align:right;padding-right:50px\">"
        + "         <div>"
        + "           <span style=\"color:#0a8d40;font-size:14pt\">NO.</span>"
        + "           <span style=\"color:#115fad;font-size:18pt\">"
        + getNO() + "</span>"
        + "         </div>"
        + "         <div style=\"color:#6b6e75;font-size:14pt\">"
        + DateFormateUtil.dateFormat(new Date())
        + "         </div>"
        + "       </td>"
        + "      </tr>"
        + "     </tbody>"
        + "   </table> "
        + "  </td>"
        + "</tr>";
  }

  /**
   * 获取期刊号，例如：2020-30
   */
  private String getNO() {
    return Calendar.getInstance().get(Calendar.YEAR) + "-" + Calendar.getInstance()
        .get(Calendar.WEEK_OF_YEAR);
  }

  /**
   * 邮件内容网站列表
   *
   * @param siteHomeSearchData 网站列表
   * @return 邮件内容网站列表
   */
  private String getSiteTableList(SiteHomeSearchData siteHomeSearchData) {
    StringBuilder stringBuilderContent = new StringBuilder();

    List<SiteHomeSearchResp> siteHomeSearchRespList = siteHomeSearchData.getList();
    for (int i = 0, size = siteHomeSearchRespList.size(); i < size; i++) {
      SiteHomeSearchResp siteHomeSearchResp = siteHomeSearchRespList.get(i);
      stringBuilderContent.append("<tr>"
          + "   <td>"
          + "     <table width=\"700px\" bgcolor=\"#ffffff\" style=\"margin-left: auto; margin-right: auto; padding-right: 10px; padding-left: 10px;\">"
          + "       <tbody>"
          + "         <tr>"
          + "          <td>"
          + "           <table width=\"674px\" style=\"margin-left: auto; margin-right: auto; padding-left: 20px; padding-right: 20px; padding-top: 20px; padding-bottom: 20px; \" cellspacing=\"0\" cellpadding=\"0\">"
          + "            <tbody>"
          + "             <tr>"
          + "              <td style=\"font-size: 18px; height: 30px; vertical-align: top;\"> <a style=\"text-decoration: none;font-size:18px;font-weight:500; \" href=\"");
      stringBuilderContent.append(siteHomeSearchResp.getUrl());
      stringBuilderContent.append("\" target=\"_blank\" class=\"mycolor\">");
      stringBuilderContent.append(siteHomeSearchResp.getName());
      stringBuilderContent.append("</a></td>"
          + "             </tr>"
          + "             <tr>"
          + "              <td style=\"padding-bottom:15px\"> <span style=\"display: inline-block;min-width: 10px;padding: 3px 15px;font-size: 11px;line-height: 1;vertical-align: baseline;white-space: nowrap;text-align: center;background-color: #eeeeee;border-radius: 10px; color: #666666;\">");
      stringBuilderContent.append(
          StringUtils.isEmpty(siteHomeSearchResp.getTag()) ? "" : siteHomeSearchResp.getTag());
      stringBuilderContent.append("</span> </td>"
          + "             </tr>"
          + "             <tr>"
          + "              <td style=\"color: #666666; font-size: 14px; line-height: 1.6em;\">");
      stringBuilderContent.append(StringUtils.isEmpty(siteHomeSearchResp.getRemark()) ? ""
          : siteHomeSearchResp.getRemark());
      stringBuilderContent.append("</td>"
          + "             </tr>"
          + "            </tbody>"
          + "           </table>"
          + "          </td>"
          + "         </tr>"
          + "        </tbody>"
          + "       </table> "
          + "  </td>"
          + "</tr> ");
    }

    return stringBuilderContent.toString();
  }

  /**
   * 获取订阅信息
   */
  private String getSubTable() {
    String subUrl = siteAddress + "/sub";
    return
        "<table width=\"700px\" bgcolor=\"#ffffff\" style=\"margin-top: auto; margin-left: auto; margin-right: auto;\" cellspacing=\"0\" cellpadding=\"0\">"
            + "    <tbody>"
            + "     <tr>"
            + "      <td height=\"50px\" span=\"2\"></td>"
            + "     </tr>"
            + "     <tr>"
            + "      <td width=\"515px\">"
            + "       <table width=\"515px\" style=\"padding-left:20px;padding-right:20px\">"
            + "        <tbody>"
            + "         <tr height=\"25px\" style=\"vertical-align: top;\">"
            + "          <td style=\"font-size: 14px;\">感谢您订阅「爱收藏-每周经典网站」<span style=\"color:red\">每周日</span>给订阅者发送每周网站精要邮件。 </td>"
            + "         </tr>"
            + "         <tr height=\"35px\" style=\"vertical-align: top;\">"
            + "          <td style=\"font-size: 14px;\"><span>别人转发给你的邮件？</span><a href=\""
            + subUrl
            + "\" target=\"_blank\" style=\"color: #0f957b; text-decoration: none;\">现在订阅您自己的爱收藏-每周经典网站邮件吧</a><span>。</span></td>"
            + "         </tr>"
            + "         <tr height=\"20px\">"
            + "          <td style=\"font-size: 14px;\"><img src=\"" + siteAddress
            + "/static/img/weibo.png\" style=\"margin-right: 10px;\" height=\"20\" width=\"20\" /><span>爱收藏微博：</span><a href=\"http://weibo.com/u/1718711654\" target=\"_blank\" style=\"color: #0f957b; text-decoration: none;\">闯荡南北的鱼</a></td>"
            + "         </tr>"
            + "         <tr height=\"20px\">"
            + "          <td style=\"font-size: 14px;\"><img src=\"" + siteAddress
            + "/static/img/weixin.png\" style=\"margin-right: 10px;\" height=\"20\" width=\"20\" /><span>爱收藏微信：leanfisher</span></td>"
            + "         </tr>"
            + "         <tr height=\"20px\">"
            + "          <td style=\"font-size: 14px;\"><img src=\"" + siteAddress
            + "/static/img/email.jpg\" style=\"margin-right: 10px;\" height=\"20\" width=\"20\"/><span>爱收藏邮箱：leanfish2011@163.com</span></td>"
            + "         </tr>"
            + "         <tr height=\"20px\">"
            + "                    <td style=\"font-size: 14px;\"><img src=\"" + siteAddress
            + "/static/img/blog.jpg\" style=\"margin-right: 10px;\" height=\"20\" width=\"20\"/><span>博客：</span><a href=\"https://www.cnblogs.com/leanfish\" target=\"_blank\" style=\"color: #0f957b; text-decoration: none;\">瘦鱼 - 博客园</a></td> "
            + "         </tr>"
            + "        </tbody>"
            + "       </table></td>"
            + "      <td><img height=\"184\" width=\"184\" src=\"" + siteAddress
            + "/static/img/weixinsite.jpg\" /></td>"
            + "     </tr>"
            + "    </tbody>"
            + "   </table>";
  }

  /**
   * 取消订阅
   *
   * @param subId 当前订阅者邮箱id
   * @return 取消订阅信息
   */
  private String getUnSubInfo(String subId) {
    String unSubUrl = siteAddress + "/unsub?subid=" + subId;
    return "<div style=\"text-align:center\">"
        + "  <div style=\"border-top:1px solid #ddd;width: 600px;display:inline-block;padding:10px\">"
        + "    <a style=\"display:inline-block;background:#ddd;border-radius:4px;padding: 3px 15px;color:#a6a6a6;text-decoration:none;font-size:12px\" href=\""
        + unSubUrl + "\">点击这里取消订阅</a>"
        + "  </div>\n"
        + "</div> ";
  }
}
