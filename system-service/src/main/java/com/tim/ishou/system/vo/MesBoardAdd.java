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
  private String userId;

  @NotBlank
  private String userName;

  @NotBlank
  private String userPhoto;

  @NotBlank
  private String content;

  @NotBlank
  private String parentId;
}
