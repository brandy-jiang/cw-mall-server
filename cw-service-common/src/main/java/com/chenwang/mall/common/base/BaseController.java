package com.chenwang.mall.common.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class BaseController {
  protected static final int PAGE_SIZE = 15;
  public static final Sort DEFAULT_SORT = new Sort(Sort.Direction.DESC, "createTime")
      .and(new Sort(Sort.Direction.ASC, "id"));
  protected Gson gson = new GsonBuilder().serializeNulls().create();


  public HttpServletRequest getRequest() {
    return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
  }


//  /**
//   * 验证码 是否正确 param code return
//   */
//  protected Boolean checkCode(String phone, String code, int codeType) {
//    try {
//      String cacheStr = redisTemplate.opsForValue().get(code + ":" + phone);
//      if (StringUtils.isEmpty(cacheStr)) {
//        return false;
//      }
//      RandomDto randomDto = gson.fromJson(cacheStr, RandomDto.class);
//      if (org.apache.commons.lang.StringUtils.equals(randomDto.getCode(), code)
//          && randomDto.getCodeType() == codeType
//          && StringUtils.equals(randomDto.getPhone(), phone)
//          && System.currentTimeMillis() < randomDto.getTime()) {
//        return true;
//      }
//    } catch (Exception e) {
//      log.warn("failed to parse checkcode: ", e);
//    }
//    return false;
//  }
//
//
//  public JSONObject getOperatorLimit() {
//    JSONObject jsonObject = new JSONObject();
//    jsonObject.put("money", redisTemplate.opsForValue().get("LIMIT_WITHDRAW"));
//    return jsonObject;
//  }
}
