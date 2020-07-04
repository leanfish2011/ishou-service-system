package com.tim.ishou.system.component;

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

  public void sendMimeMessge(String to, String subject, String content)
      throws javax.mail.MessagingException {
    MimeMessage message = mailSender.createMimeMessage();
    //true表示需要创建一个multipart message
    MimeMessageHelper helper = new MimeMessageHelper(message, true);
    helper.setFrom(from);
    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(content, true);

    mailSender.send(message);
  }
}
