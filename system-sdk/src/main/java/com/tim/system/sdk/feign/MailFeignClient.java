package com.tim.system.sdk.feign;

import com.tim.config.FeignConfiguration;
import com.tim.message.Message;
import com.tim.system.sdk.po.MailReq;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "${ishou-service-system.name:ishou-service-system}", path = "/api/ishou/v2/system/mail", configuration = FeignConfiguration.class)
public interface MailFeignClient {

  @RequestMapping(value = "/post", method = RequestMethod.POST)
  Message<Boolean> post(@RequestBody MailReq mailReq);
}
