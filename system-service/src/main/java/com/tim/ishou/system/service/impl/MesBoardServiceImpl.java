package com.tim.ishou.system.service.impl;

import com.tim.auth.sdk.component.AccountInfo;
import com.tim.auth.sdk.vo.LoginResp;
import com.tim.ishou.system.dao.MesBoardMapper;
import com.tim.ishou.system.po.MesBoard;
import com.tim.ishou.system.po.MesBoardExample;
import com.tim.ishou.system.service.MesBoardService;
import com.tim.ishou.system.vo.MesBoardAdd;
import com.tim.ishou.system.vo.MesBoardSearchData;
import com.tim.ishou.system.vo.MesBoardSearchRes;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
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

  @Autowired
  private AccountInfo accountInfo;

  @Override
  public Boolean add(MesBoardAdd mesBoardAdd) {
    MesBoard mesBoard = new MesBoard();
    BeanUtils.copyProperties(mesBoardAdd, mesBoard);
    mesBoard.setId(UUID.randomUUID().toString());

    LoginResp loginResp = accountInfo.getUserInfo().getLoginResp();
    mesBoard.setUserId(loginResp.getUserId());
    mesBoard.setUserName(loginResp.getName());
    //TODO增加用户头像
    mesBoard.setUserPhoto("TODO");

    return mesBoardMapper.insertSelective(mesBoard) > 0 ? true : false;
  }

  @Override
  public MesBoardSearchData list() {
    MesBoardExample mesBoardExample = new MesBoardExample();
    mesBoardExample.setOrderByClause("create_time desc");
    List<MesBoard> mesBoardList = mesBoardMapper.selectByExample(mesBoardExample);

    List<MesBoardSearchRes> mesBoardSearchResList = new ArrayList<>();
    mesBoardList.stream().forEach(
        mesBoard -> {
          MesBoardSearchRes mesBoardSearchRes = new MesBoardSearchRes();
          BeanUtils.copyProperties(mesBoard, mesBoardSearchRes);

          if (StringUtils.isNotEmpty(mesBoard.getParentId())) {
            mesBoardList.stream().filter(mes -> mes.getId().equals(mesBoard.getParentId()))
                .findFirst().ifPresent(m -> mesBoardSearchRes.setParentUserName(m.getUserName()));
          }

          mesBoardSearchResList.add(mesBoardSearchRes);
        }
    );

    MesBoardSearchData mesBoardSearchData = new MesBoardSearchData();
    mesBoardSearchData.setList(mesBoardSearchResList);
    mesBoardSearchData.setTotal(mesBoardSearchResList.size());

    return mesBoardSearchData;
  }

  @Override
  public Boolean delete(String id) {
    return mesBoardMapper.deleteByPrimaryKey(id) == 1 ? true : false;
  }
}
