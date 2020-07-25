package com.tim.ishou.system.job;

import com.tim.ishou.site.feign.SiteFeignClient;
import com.tim.ishou.site.vo.SiteHomeSearchData;
import com.tim.ishou.site.vo.SiteHomeSearchResp;
import com.tim.ishou.system.component.MailHandle;
import com.tim.ishou.system.service.SiteSubService;
import com.tim.message.MainCode;
import com.tim.message.Message;
import com.tim.util.DateFormateUtil;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author：tim
 * @date：20-7-4 下午10:20
 * @description：网址发布服务
 */
@Component
@Slf4j
public class SitePubJob {

  @Value("${home.site.default.icon:http://106.53.116.69/static/ishou.ico}")
  private String defaultIcon;

  @Autowired
  private MailHandle mailHandle;

  @Autowired
  private SiteFeignClient siteFeignClient;

  @Autowired
  private SiteSubService siteSubService;

  @Value("${home.site.pub.switch:true}")
  private boolean pubSwitch;

  /**
   * 首页网站发布
   */
  @Scheduled(cron = "${home.site.pub.cron:0 0 9 ? * 2}")
  @Scheduled(fixedRate = 3000)
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
    String content = getEmailContent(subject, siteHomeSearchData);

    List<String> emailList = siteSubService.getSubEmail();
    if (emailList == null || emailList.size() == 0) {
      log.warn("没有主页网址订阅者！");
    }

    emailList.stream().parallel().forEach(
        email -> mailHandle.sendMimeMessge(email, subject, content)
    );

  }

  /**
   * 生成邮件内容
   *
   * @param title 邮件内容题目
   * @param siteHomeSearchData 主页网址数据
   * @return 邮件内容
   */
  private String getEmailContent(String title, SiteHomeSearchData siteHomeSearchData) {
    StringBuilder stringBuilderContent = new StringBuilder();
    stringBuilderContent
        .append("<table width=\"500\" align=\"center\" cellspacing=\"0\" cellpadding=\"6\">");
    stringBuilderContent.append("<caption>");
    stringBuilderContent.append(title);
    stringBuilderContent.append("</caption>");
    stringBuilderContent.append("    <thead>");
    stringBuilderContent.append("        <tr align=\"center\">");
    stringBuilderContent.append("            <th>序号</th>");
    stringBuilderContent.append("            <th>图标</th>");
    stringBuilderContent.append("            <th>网址</th>");
    stringBuilderContent.append("            <th>创建时间</th>");
    stringBuilderContent.append("        </tr>");
    stringBuilderContent.append("    </thead>");
    stringBuilderContent.append("    <tbody>");

    List<SiteHomeSearchResp> siteHomeSearchRespList = siteHomeSearchData.getList();
    for (int i = 0, size = siteHomeSearchRespList.size(); i < size; i++) {
      SiteHomeSearchResp siteHomeSearchResp = siteHomeSearchRespList.get(i);

      stringBuilderContent.append("<tr align=\"center\">");
      stringBuilderContent.append("<td>");
      stringBuilderContent.append(i + 1);
      stringBuilderContent.append("</td>");

      stringBuilderContent.append("<td><img height=\"30\" width=\"30\" src=\"");
      if (StringUtils.isEmpty(siteHomeSearchResp.getIconUrl())) {
        stringBuilderContent.append(defaultIcon);
      } else {
        stringBuilderContent.append(siteHomeSearchResp.getIconUrl());
      }

      stringBuilderContent.append("\" /></td>");
      stringBuilderContent.append("<td><a href=\"");
      stringBuilderContent.append(siteHomeSearchResp.getUrl());
      stringBuilderContent.append("\" target=\"_blank\">");
      stringBuilderContent.append(siteHomeSearchResp.getName());
      stringBuilderContent.append("</a></td>");

      stringBuilderContent.append("<td>");
      stringBuilderContent
          .append(DateFormateUtil.date2String(siteHomeSearchResp.getCreateTime()));
      stringBuilderContent.append("</td>");
    }
    stringBuilderContent.append("</tbody></table>");

    return stringBuilderContent.toString();
  }

}
