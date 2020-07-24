package com.tim.ishou.system.service;

import com.tim.ishou.system.vo.SiteSubAdd;
import com.tim.ishou.system.vo.SiteSubSearchReq;

/**
 * @author：tim
 * @date：20-7-24 下午10:45
 * @description：
 */
public interface SiteSubService {

  Boolean add(SiteSubAdd siteSubAdd);

  Boolean exist(SiteSubSearchReq siteSubSearchReq);

  Boolean delete(String id);
}
