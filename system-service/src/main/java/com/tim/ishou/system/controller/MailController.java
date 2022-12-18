package com.tim.ishou.system.controller;

import com.tim.ishou.system.component.MailHandle;
import com.tim.message.Message;
import com.tim.system.sdk.po.MailReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author：tim
 * @date：2022-12-18 4:58 PM
 * @description：邮件处理接口
 */
@Api(tags = "邮件接口")
@RestController
@RequestMapping("${api.version.module}/mail")
public class MailController {

  @Autowired
  private MailHandle mailHandle;

  @ApiOperation(value = "发送邮件")
  @RequestMapping(value = "/post", method = RequestMethod.POST)
  public Message post(@RequestBody MailReq mailReq) {
    mailHandle.sendMimeMessge(mailReq);
    return Message.success();
  }
}
