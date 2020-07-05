package com.tim.ishou.system.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Test;

/**
 * @author：tim
 * @date：20-7-5 上午11:50
 * @description：
 */
public class TestDateFormat {

  @Test
  public void testFormatdate() {
    //使用静态方法获取实例。只能格式化日期
    DateFormat df1 = DateFormat.getDateInstance();
    //只能格式化时间
    DateFormat df2 = DateFormat.getTimeInstance();
    //格式化日期时间
    DateFormat df3 = DateFormat.getDateTimeInstance();
    //要格式化的Date对象
    Date date = new Date();
    //使用format()格式化Date对象
    System.out.println(df1.format(date));
    System.out.println(df2.format(date));
    System.out.println(df3.format(date));
  }

  @Test
  public void testSimpleFormate() {
    //创建SimpleDateFormat对象，指定样式    2019-05-13 22:39:30
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //2019年的第133天。占位符是特定的
    SimpleDateFormat sdf2 = new SimpleDateFormat("y年的第D天");
    //要格式化的Date对象
    Date date = new Date();

    //使用format()方法格式化Date对象为字符串，返回字符串
    System.out.println(sdf1.format(date));
    System.out.println(sdf2.format(date));
  }

}
