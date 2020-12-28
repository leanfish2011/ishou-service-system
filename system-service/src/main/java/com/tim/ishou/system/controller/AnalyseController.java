package com.tim.ishou.system.controller;

import com.tim.ishou.system.service.AnalyseService;
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
 * @date： 2020-12-28 下午10:53
 * @description：
 */
@Api(description = "分析接口")
@RestController
@RequestMapping("${api.version.module}/analyse")
public class AnalyseController {

  @Autowired
  private AnalyseService analyseService;

  @ApiOperation(value = "分析文本")
  @RequestMapping(value = "/text", method = RequestMethod.POST)
  public Message<Boolean> text(@RequestBody String content) {
    return Message.success(analyseService.isTextLegal(content));
  }
}
