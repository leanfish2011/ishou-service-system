package com.tim.ishou.system.dao;

import com.tim.ishou.system.po.SiteSub;
import com.tim.ishou.system.po.SiteSubExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SiteSubMapper {

  int countByExample(SiteSubExample example);

  int deleteByExample(SiteSubExample example);

  int deleteByPrimaryKey(String id);

  int insert(SiteSub record);

  int insertSelective(SiteSub record);

  List<SiteSub> selectByExample(SiteSubExample example);

  SiteSub selectByPrimaryKey(String id);

  int updateByExampleSelective(@Param("record") SiteSub record,
      @Param("example") SiteSubExample example);

  int updateByExample(@Param("record") SiteSub record, @Param("example") SiteSubExample example);

  int updateByPrimaryKeySelective(SiteSub record);

  int updateByPrimaryKey(SiteSub record);
}
