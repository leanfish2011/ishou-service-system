package com.tim.ishou.system.config;

import com.baidu.aip.contentcensor.AipContentCensor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author：tim
 * @date： 2020-12-28 下午10:34
 * @description：百度分析接口
 */
@Configuration
public class AipContentCensorConfig {

  @Value("${analyze.api.app_id}")
  private String appId;

  @Value("${analyze.api.api_key}")
  private String apiKey;

  @Value("${analyze.api.secret_key}")
  private String secretKey;

  @Bean
  public AipContentCensor createAipContentCensor() {
    AipContentCensor aipContentCensor = new AipContentCensor(appId, apiKey, secretKey);

    // 可选：设置网络连接参数
    aipContentCensor.setConnectionTimeoutInMillis(2000);
    aipContentCensor.setSocketTimeoutInMillis(60000);

    return aipContentCensor;
  }
}
