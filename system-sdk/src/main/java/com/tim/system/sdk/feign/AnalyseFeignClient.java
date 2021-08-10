package com.tim.system.sdk.feign;

import com.tim.config.FeignConfiguration;
import com.tim.message.Message;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "${ishou-service-system.name:ishou-service-system}", path = "/api/ishou/v2/system/analyse", configuration = FeignConfiguration.class)
public interface AnalyseFeignClient {

  @RequestMapping(value = "/text", method = RequestMethod.POST)
  Message<Boolean> text(@RequestBody String content);

}
