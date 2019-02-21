package com.chenwang.mall.wechat.common;

import com.chenwang.mall.dto.WxUserInfoDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 微信公众号 校验token
 */
@Slf4j
public class SignUtil {
  private static String token = "ac3d98db634d42d1b38f97fce4f5ac20";
  static Gson gson = new GsonBuilder().serializeNulls().create();

  /**
   * 校验签名
   *
   * @param signature 签名
   * @param timestamp 时间戳
   * @param nonce     随机数
   * @return 布尔值
   */
  public static boolean checkSignature(String signature, String timestamp, String nonce) {
    String checkText = null;
    if (null != signature) {
      //对ToKen,timestamp,nonce 按字典排序
      String[] paramArr = new String[] {token, timestamp, nonce};
      Arrays.sort(paramArr);
      //将排序后的结果拼成一个字符串
      String content = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);

      try {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        //对接后的字符串进行sha1加密
        byte[] digest = md.digest(content.getBytes());
        checkText = byteToStr(digest);
      } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
      }
    }
    //将加密后的字符串与signature进行对比
    return checkText != null && checkText.equals(signature.toUpperCase());
  }

  /**
   * 将字节数组转化我16进制字符串
   *
   * @param byteArrays 字符数组
   * @return 字符串
   */
  private static String byteToStr(byte[] byteArrays) {
    StringBuilder str = new StringBuilder();
    for (byte byteArray : byteArrays) {
      str.append(byteToHexStr(byteArray));
    }
    return str.toString();
  }

  /**
   * 将字节转化为十六进制字符串
   *
   * @param myByte 字节
   * @return 字符串
   */
  private static String byteToHexStr(byte myByte) {
    char[] digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    char[] tampArr = new char[2];
    tampArr[0] = digit[(myByte >>> 4) & 0X0F];
    tampArr[1] = digit[myByte & 0X0F];
    return new String(tampArr);
  }

//  public static String getSha1(Map<String, String> map) {
//    if (CollectionUtils.isEmpty(map)) {
//      return null;
//    }
//    Map<String, String> sortMap = PayCommonUtils.sortMapByKey(map);
//    StringBuilder builder = new StringBuilder();
//    Set<Map.Entry<String, String>> entries = sortMap.entrySet();
//    for (Map.Entry<String, String> next : entries) {
//      builder.append(next.getKey()).append("=").append(next.getValue()).append("&");
//    }
//    String str = builder.delete(builder.length() - 1, builder.length()).toString();
//    char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
//        'a', 'b', 'c', 'd', 'e', 'f'};
//    try {
//      MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
//      mdTemp.update(str.getBytes("UTF-8"));
//
//      byte[] md = mdTemp.digest();
//      int jee = md.length;
//      char[] buf = new char[jee * 2];
//      int key = 0;
//      for (byte byte0 : md) {
//        buf[key++] = hexDigits[byte0 >>> 4 & 0xf];
//        buf[key++] = hexDigits[byte0 & 0xf];
//      }
//      return new String(buf);
//    } catch (Exception e) {
//      return null;
//    }
//  }

  public static String getSha1(String string) {
    if (StringUtils.isEmpty(string)) {
      return null;
    }
    char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f'};
    try {
      MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
      mdTemp.update(string.getBytes("UTF-8"));
      byte[] md = mdTemp.digest();
      int jee = md.length;
      char[] buf = new char[jee * 2];
      int key = 0;
      for (byte byte0 : md) {
        buf[key++] = hexDigits[byte0 >>> 4 & 0xf];
        buf[key++] = hexDigits[byte0 & 0xf];
      }
      return new String(buf);
    } catch (Exception e) {
      return null;
    }
  }

  public static OAuthAccessToken getOAuthAccessToken(String appid, String secret, String code) {
    OAuthAccessToken token = WxApi.getOAuthAccessToken(appid, secret, code);
    if (token != null) {
      if (token.getErrcode() != null) {
        log.error("## getOAuthAccessToken Error = " + token.getErrmsg());
      } else {
        return token;
      }
    }
    return null;
  }

  public static WxUserInfoDto getWeiXinAppUserInfo(String accessToken, String openId) {
    String url = WxApi.getAppFansInfoUrl(accessToken, openId);
    JSONObject jsonObject = WxApi.httpsRequest(url, "GET");
    return gson.fromJson(jsonObject.toString(), new TypeToken<WxUserInfoDto>(){}.getType());
  }

}