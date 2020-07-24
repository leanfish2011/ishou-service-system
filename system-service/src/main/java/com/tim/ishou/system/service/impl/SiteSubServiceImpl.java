package com.tim.ishou.system.service.impl;

import com.tim.ishou.system.dao.SiteSubMapper;
import com.tim.ishou.system.po.SiteSub;
import com.tim.ishou.system.po.SiteSubExample;
import com.tim.ishou.system.po.SiteSubExample.Criteria;
import com.tim.ishou.system.service.SiteSubService;
import com.tim.ishou.system.vo.SiteSubAdd;
import com.tim.ishou.system.vo.SiteSubSearchReq;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author：tim
 * @date： 2020-07-24 下午10:53
 * @description：
 */
@Service
public class SiteSubServiceImpl implements SiteSubService {

  @Autowired
  private SiteSubMapper siteSubMapper;

  @Override
  public Boolean add(SiteSubAdd siteSubAdd) {
    SiteSub siteSub = new SiteSub();
    siteSub.setEmail(siteSubAdd.getEmail());
    siteSub.setId(UUID.randomUUID().toString());

    return siteSubMapper.insertSelective(siteSub) > 0 ? true : false;
  }

  @Override
  public Boolean exist(SiteSubSearchReq siteSubSearchReq) {
    SiteSubExample siteSubExample = new SiteSubExample();
    Criteria criteria = siteSubExample.createCriteria();
    criteria.andEmailEqualTo(siteSubSearchReq.getEmail());

    return siteSubMapper.countByExample(siteSubExample) > 0 ? true : false;
  }

  @Override
  public Boolean delete(String id) {
    return siteSubMapper.deleteByPrimaryKey(id) > 0 ? true : false;
  }
}
