package com.tim.ishou.system.service;

import com.tim.ishou.system.vo.MesBoardAdd;
import com.tim.ishou.system.vo.MesBoardSearchData;

/**
 * @author：tim
 * @date： 2020-08-22 下午5:05
 * @description：
 */
public interface MesBoardService {

  Boolean add(MesBoardAdd mesBoardAdd);

  MesBoardSearchData list();

  Boolean delete(String id);

}
