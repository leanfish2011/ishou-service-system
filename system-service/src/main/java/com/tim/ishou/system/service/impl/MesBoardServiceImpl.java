package com.tim.ishou.system.service.impl;

import com.tim.ishou.system.dao.MesBoardMapper;
import com.tim.ishou.system.po.MesBoard;
import com.tim.ishou.system.service.MesBoardService;
import com.tim.ishou.system.vo.MesBoardAdd;
import java.util.UUID;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author：tim
 * @date： 2020-08-22 下午5:06
 * @description：
 */
@Service
public class MesBoardServiceImpl implements MesBoardService {

  @Autowired
  private MesBoardMapper mesBoardMapper;

  @Override
  public Boolean add(MesBoardAdd mesBoardAdd) {
    MesBoard mesBoard = new MesBoard();
    BeanUtils.copyProperties(mesBoardAdd, mesBoard);
    mesBoard.setId(UUID.randomUUID().toString());

    return mesBoardMapper.insertSelective(mesBoard) > 0 ? true : false;
  }
}
