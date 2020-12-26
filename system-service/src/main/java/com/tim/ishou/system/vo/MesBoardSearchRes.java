package com.tim.ishou.system.vo;

import java.util.Date;
import lombok.Data;

/**
 * @author：tim
 * @date： 2020-12-26 下午4:09
 * @description：
 */
@Data
public class MesBoardSearchRes {

  private String id;

  private String userId;

  private String userName;

  private String userPhoto;

  private String content;

  private String parentId;

  private Date createTime;
}
