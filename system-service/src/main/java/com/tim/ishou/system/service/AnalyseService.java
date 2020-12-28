package com.tim.ishou.system.service;

/**
 * @author：tim
 * @date： 2020-12-28 下午10:17
 * @description：百度分析
 */
public interface AnalyseService {

  /**
   * 分析文本是否合规
   *
   * @param content 文本
   * @return 时候合规
   */
  Boolean isTextLegal(String content);
}
