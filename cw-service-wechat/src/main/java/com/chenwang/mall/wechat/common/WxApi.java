package com.chenwang.mall.wechat.common;


import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Objects;

public class WxApi {

  private static final Logger _log = LoggerFactory.getLogger(WxApi.class);

  /**
   * 申请退款接口
   */
  public static final String PAY_REFUND = "https://api.mch.weixin.qq.com/secapi/pay/refund";

  /**
   * 给用户打款接口
   */
  public static final String PAY_PLAY = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

  /**
   * token 接口
   */
  private static final String TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

  /**
   * 获取账号粉丝信息
   */
  private static final String GET_FANS_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";

  /**
   * 网页授权OAuth2.0获取code
   */
  private static final String GET_OAUTH_CODE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=%s&scope=%s&state=%s#wechat_redirect";

  /**
   * 网页授权OAuth2.0获取token
   */
  private static final String GET_OAUTH_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

  private static final String GET_OAUTH_REFRESH_TOKEN = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s";

  /**
   * 网页授权OAuth2.0获取用户信息
   */
  private static final String GET_OAUTH_USERINFO = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

  /**
   * js ticket
   */
  private static final String GET_JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";

  /**
   * 发送客服消息
   */
  private static final String SEND_CUSTOM_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s";

  /**
   * 微信小程序获取openid
   */
  private static final String XIAO_CHENG_XU_CODE = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&grant_type=authorization_code&js_code=%s";

  /**
   * 获取小程序获取openid
   */
  public static String getXcxOpenId(String appId, String appSecret, String code) {
    return String.format(XIAO_CHENG_XU_CODE, appId, appSecret, code);
  }

  //获取token接口
  public static String getTokenUrl(String appId, String appSecret) {
    return String.format(TOKEN, appId, appSecret);
  }

  //获取粉丝信息接口
  public static String getFansInfoUrl(String token, String openid) {
    return String.format(GET_FANS_INFO, token, openid);
  }

  public static String getAppFansInfoUrl(String token, String openid) {
    return String.format(GET_OAUTH_USERINFO, token, openid);
  }

  //网页授权OAuth2.0获取code
  public static String getOAuthCodeUrl(String appId, String redirectUrl, String scope, String state) {
    return String.format(GET_OAUTH_CODE, appId, urlEnodeUTF8(redirectUrl), "code", scope, state);
  }

  //网页授权OAuth2.0获取token
  public static String getOAuthTokenUrl(String appId, String appSecret, String code) {
    return String.format(GET_OAUTH_TOKEN, appId, appSecret, code);
  }

  public static String getRefreshTokenUrl(String appId, String refreshToken) {
    return String.format(GET_OAUTH_REFRESH_TOKEN, appId, refreshToken);
  }

  //网页授权OAuth2.0获取用户信息
  public static String getOAuthUserinfoUrl(String token, String openid) {
    return String.format(GET_OAUTH_USERINFO, token, openid);
  }

  //获取js ticket url
  public static String getJsApiTicketUrl(String token) {
    return String.format(GET_JSAPI_TICKET, token);
  }

  //获取发送客服消息 url
  public static String getSendCustomMessageUrl(String token) {
    return String.format(SEND_CUSTOM_MESSAGE, token);
  }

  //获取OAuth2.0 Token
  public static OAuthAccessToken getOAuthAccessToken(String appId, String appSecret, String code) {
    OAuthAccessToken token = null;
    String tockenUrl = getOAuthTokenUrl(appId, appSecret, code);
    JSONObject jsonObject = httpsRequest(tockenUrl, "GET", null);
    if (Objects.nonNull(jsonObject) && !jsonObject.has("errcode")) {
      try {
        token = new OAuthAccessToken();
        token.setAccessToken(jsonObject.getString("access_token"));
        token.setExpiresIn(jsonObject.getInt("expires_in"));
        token.setOpenid(jsonObject.getString("openid"));
        token.setScope(jsonObject.getString("scope"));
        token.setOauthAccessToken(jsonObject.getString("refresh_token"));
        if(jsonObject.has("unionid"))
          token.setUnionid(jsonObject.getString("unionid"));
      } catch (JSONException e) {
        token = null;
      }
    } else if (null != jsonObject) {
      token = new OAuthAccessToken();
      token.setErrcode(jsonObject.getInt("errcode"));
    }
    return token;
  }

  /**
   * 获取OAuth2.0 Token
   */
  public static OAuthAccessToken getOAuthAccessTokenXcx(String appId, String appSecret, String code) {
    OAuthAccessToken token = null;
    String tockenUrl = getXcxOpenId(appId, appSecret, code);
    JSONObject jsonObject = httpsRequest(tockenUrl, "GET", null);
    if (null != jsonObject && !jsonObject.has("errcode")) {
      try {
        token = new OAuthAccessToken();
        token.setAccessToken(jsonObject.getString("access_token"));
        token.setExpiresIn(jsonObject.getInt("expires_in"));
        token.setOpenid(jsonObject.getString("openid"));
        token.setScope(jsonObject.getString("scope"));
        token.setOauthAccessToken(jsonObject.getString("refresh_token"));
      } catch (JSONException e) {
        _log.error("e -> {}", e);
        token = null;
      }
    } else if (null != jsonObject) {
      token = new OAuthAccessToken();
      token.setErrcode(jsonObject.getInt("errcode"));
    }
    return token;
  }


  //发送请求
  public static JSONObject httpsRequest(String requestUrl, String requestMethod) {
    return httpsRequest(requestUrl, requestMethod, null);
  }

  public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
    JSONObject jsonObject = null;
    try {
      _log.info("发送HTTPS请求,requestMethod={},requestUrl={},outputStr={}", requestMethod, requestUrl, outputStr);
      TrustManager[] tm = {new JEEWeiXinX509TrustManager()};
      SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
      sslContext.init(null, tm, new java.security.SecureRandom());
      SSLSocketFactory ssf = sslContext.getSocketFactory();

      URL url = new URL(requestUrl);
      HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
      conn.setSSLSocketFactory(ssf);

      conn.setDoOutput(true);
      conn.setDoInput(true);
      conn.setUseCaches(false);
      conn.setRequestMethod(requestMethod);
      if (null != outputStr) {
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(outputStr.getBytes("UTF-8"));
        outputStream.close();
      }
      InputStream inputStream = conn.getInputStream();
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
      String str = null;
      StringBuffer buffer = new StringBuffer();
      while ((str = bufferedReader.readLine()) != null) {
        buffer.append(str);
      }
      bufferedReader.close();
      inputStreamReader.close();
      inputStream.close();
      inputStream = null;
      conn.disconnect();
      _log.info("响应数据,rtn={}", buffer);
      jsonObject = new JSONObject(buffer.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return jsonObject;
  }

  //获取接口访问凭证
  public static AccessToken getAccessToken(String appId, String appSecret) {
    AccessToken token = null;
    String tockenUrl = WxApi.getTokenUrl(appId, appSecret);
    JSONObject jsonObject = httpsRequest(tockenUrl, "GET", null);
    if (null != jsonObject && !jsonObject.has("errcode")) {
      try {
        token = new AccessToken();
        token.setAccessToken(jsonObject.getString("access_token"));
        token.setExpiresIn(jsonObject.getInt("expires_in"));
      } catch (JSONException e) {
        token = null;
      }
    } else if (null != jsonObject) {
      token = new AccessToken();
      token.setErrcode(jsonObject.getInt("errcode"));
    }
    return token;
  }

  /**
   * 获取jsapi_ticket 是公众号用于调用微信JS接口的临时票据。
   *
   * @param accessToken 接口访问凭证
   * @return JSTicket
   */
  public static JSTicket getJsTicket(String accessToken) {
    JSTicket jsTicket = null;
    String ticketUrl = WxApi.getJsApiTicketUrl(accessToken);
    JSONObject jsonObject = httpsRequest(ticketUrl, "GET", null);
    if (Objects.nonNull(jsonObject) && jsonObject.getString("errcode").equals("0")) {
      try {
        jsTicket = new JSTicket();
        jsTicket.setTicket(jsonObject.getString("ticket"));
        jsTicket.setExpiresIn(jsonObject.getInt("expires_in"));
      } catch (JSONException e) {
        jsTicket = null;
      }
    } else if (Objects.nonNull(jsonObject)) {
      jsTicket = new JSTicket();
      jsTicket.setErrcode(jsonObject.getInt("errcode"));
      jsTicket.setErrmsg(jsonObject.getString("errmsg"));
      _log.debug("errcode : {}, errmsg : {}", jsTicket.getErrcode(), jsTicket.getErrmsg());
    }
    return jsTicket;
  }

  public static byte[] httpsRequestByte(String requestUrl, String requestMethod) {
    return httpsRequestByte(requestUrl, requestMethod, null);
  }

  public static byte[] httpsRequestByte(String requestUrl, String requestMethod, String outputStr) {
    try {
      TrustManager[] tm = {new JEEWeiXinX509TrustManager()};
      SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
      sslContext.init(null, tm, new java.security.SecureRandom());
      SSLSocketFactory ssf = sslContext.getSocketFactory();

      URL url = new URL(requestUrl);
      HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
      conn.setSSLSocketFactory(ssf);

      conn.setDoOutput(true);
      conn.setDoInput(true);
      conn.setUseCaches(false);
      conn.setRequestMethod(requestMethod);
      if (null != outputStr) {
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(outputStr.getBytes("UTF-8"));
        outputStream.close();
      }
      InputStream inputStream = conn.getInputStream();
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      byte[] buffer = new byte[4096];
      int num = 0;
      while (-1 != (num = inputStream.read(buffer))) {
        output.write(buffer, 0, num);
      }
      return output.toByteArray();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String urlEnodeUTF8(String str) {
    String result = str;
    try {
      result = URLEncoder.encode(str, "UTF-8");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  static class JEEWeiXinX509TrustManager implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
      return null;
    }
  }
}

