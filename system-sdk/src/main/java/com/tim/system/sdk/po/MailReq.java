package com.tim.system.sdk.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author：tim
 * @date：2022-12-18 5:04 PM
 * @description：邮件发送内容
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailReq {

  /**
   * 收件人邮箱
   */
  private String to;

  /**
   * 邮件主题
   */
  private String subject;

  /**
   * 邮件内容
   */
  private String content;
}
