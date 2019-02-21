package com.chenwang.mall.common.utils;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
  static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
  static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
  public static String format(Date in){
    return sdf.format(in);
  }
  public static String format3(Date in){
    return sdf3.format(in);
  }

  public static Date parseDate(String str){
    if(StringUtils.isEmpty(str))
      return null;
    try {
      return sdf2.parse(str);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }
}
