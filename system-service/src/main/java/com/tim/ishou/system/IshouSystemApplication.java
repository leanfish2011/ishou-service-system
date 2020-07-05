package com.tim.ishou.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author：tim
 * @date：20-7-4 下午10:09
 * @description：
 */
@ComponentScan({"com.tim"})
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.tim.ishou.system.dao")
@EnableFeignClients("com.tim")
@EnableScheduling
public class IshouSystemApplication {

  public static void main(String[] args) {
    SpringApplication.run(IshouSystemApplication.class, args);
  }
}
