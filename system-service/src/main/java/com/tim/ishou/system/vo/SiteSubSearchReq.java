package com.tim.ishou.system.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author：tim
 * @date：20-7-24 下午10:34
 * @description：
 */
@Data
public class SiteSubSearchReq {

  @NotBlank
  private String email;
}
