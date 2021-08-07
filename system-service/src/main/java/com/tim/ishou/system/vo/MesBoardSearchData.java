package com.tim.ishou.system.vo;

import java.util.List;
import lombok.Data;

/**
 * @author：tim
 * @date： 2020-12-26 下午9:43
 * @description：
 */
@Data
public class MesBoardSearchData {

  private int total;

  private List<MesBoardSearchRes> list;
}
