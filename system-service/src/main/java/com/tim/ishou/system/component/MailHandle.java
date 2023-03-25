package com.tim.ishou.system.component;

import com.tim.system.sdk.po.MailReq;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * @author：tim
 * @date：20-7-4 下午10:27
 * @description：邮件处理
 */
@Component
@Slf4j
public class MailHandle {

  @Autowired
  private JavaMailSender mailSender;

  @Value("${spring.mail.username:leanfish2011@163.com}")
  private String from;

  public void sendMimeMessge(MailReq mailReq) {
    MimeMessage message = mailSender.createMimeMessage();
    try {
      //true表示需要创建一个multipart message
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setFrom(from);
      helper.setTo(mailReq.getTo());
      helper.setSubject(mailReq.getSubject());
      helper.setText(mailReq.getContent(), true);
    } catch (MessagingException e) {
      log.error("发送邮件失败。", e);
      return;
    }

    mailSender.send(message);
    log.info("邮件发送成功，TO " + mailReq.getTo());
  }
}
