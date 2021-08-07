package com.tim.ishou.system.controller;

import com.tim.ishou.system.service.SiteSubService;
import com.tim.ishou.system.vo.SiteSubAdd;
import com.tim.ishou.system.vo.SiteSubSearchReq;
import com.tim.message.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author：tim
 * @date：20-7-24 下午10:36
 * @description：
 */
@Api(description = "首页网站订阅")
@RestController
@RequestMapping("${api.version.module}/site/sub")
public class SiteSubController {

  @Autowired
  private SiteSubService siteSubService;

  @ApiOperation(value = "新增网站订阅")
  @RequestMapping(method = RequestMethod.POST)
  public Message add(@RequestBody SiteSubAdd SiteSubAdd) {
    siteSubService.add(SiteSubAdd);
    return Message.success();
  }

  @ApiOperation(value = "网站订阅是否存在")
  @RequestMapping(value = "/exist", method = RequestMethod.POST)
  public Message<Boolean> exist(@RequestBody SiteSubSearchReq siteSubSearchReq) {
    return Message.success(siteSubService.exist(siteSubSearchReq));
  }

  @ApiOperation(value = "取消网站订阅")
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public Message delete(@PathVariable String id) {
    siteSubService.delete(id);
    return Message.success();
  }

}
