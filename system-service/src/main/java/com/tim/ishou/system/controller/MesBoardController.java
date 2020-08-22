package com.tim.ishou.system.controller;

import com.tim.ishou.system.service.MesBoardService;
import com.tim.ishou.system.vo.MesBoardAdd;
import com.tim.message.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author：tim
 * @date： 2020-08-22 下午5:02
 * @description：
 */
@Api(description = "留言板")
@RestController
@RequestMapping("${api.version.module}/message")
public class MesBoardController {

  @Autowired
  private MesBoardService mesBoardService;

  @ApiOperation(value = "新增留言")
  @RequestMapping(method = RequestMethod.POST)
  public Message add(@RequestBody MesBoardAdd mesBoardAdd) {
    mesBoardService.add(mesBoardAdd);
    return Message.success();
  }

}
