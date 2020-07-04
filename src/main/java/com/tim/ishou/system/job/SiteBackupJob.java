package com.tim.ishou.system.job;

import com.tim.ishou.system.component.MailHandle;
import java.util.Date;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author：tim
 * @date：20-7-4 下午10:20
 * @description：网址备份服务
 */
@Component
public class SiteBackupJob {

  @Value("${home.site.backup.mail:yangtze_yufei@163.com}")
  private String backUpMail;

  @Autowired
  private MailHandle mailHandle;

  /**
   * 首页网站备份
   */
  @Scheduled(cron = "${home.site.backup.cron:0 0 10 ? * 2}")
  public void homeSiteBackUp() throws MessagingException {
    String subject = "爱收藏首页网站备份-" + new Date();
    String content = "网站名称、网址";

    mailHandle.sendMimeMessge(backUpMail, subject, content);
  }
}
