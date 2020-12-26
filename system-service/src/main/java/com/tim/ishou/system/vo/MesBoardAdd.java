package com.tim.ishou.system.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author：tim
 * @date： 2020-08-22 下午5:03
 * @description：
 */
@Data
public class MesBoardAdd {

  @NotBlank
  private String content;

  private String parentId;
}
