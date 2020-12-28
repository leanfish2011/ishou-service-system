package com.tim.ishou.system.service.impl;

import com.baidu.aip.contentcensor.AipContentCensor;
import com.tim.ishou.system.service.AnalyseService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author：tim
 * @date： 2020-12-28 下午10:20
 * @description：
 */
@Service
@Slf4j
public class AnalyseServiceImpl implements AnalyseService {

  @Autowired
  private AipContentCensor aipContentCensor;

  @Override
  public Boolean isTextLegal(String content) {
    JSONObject response = aipContentCensor.textCensorUserDefined(content);
    log.info("文本审核结果：{}", response.toString());

    int conclusionType = response.getInt("conclusionType");

    //conclusionType 审核结果类型，可取值：1.合规，2.不合规，3.疑似，4.审核失败
    return conclusionType == 1 ? true : false;
  }
}
