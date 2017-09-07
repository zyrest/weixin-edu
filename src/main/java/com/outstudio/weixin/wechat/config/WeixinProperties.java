package com.outstudio.weixin.wechat.config;

/**
 * Created by 96428 on 2017/7/16.
 */
public class WeixinProperties {

    public static final String TOKEN = "samson";
    public static final String appID = "wxd53def0a24d17b53";
    public static final String appsecret = "14d2831e0d8153867040d95674808d40";

    public static final String BASE_SCOPE = "snsapi_base";
    public static final String USERINFO_SCOPE = "snsapi_userinfo";
    /** 需要参数 appID 和 appsecret */
    public static final String ACCESS_TOKEN_URI = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    /** 需要参数 通用access_token */
    public static final String CREATE_MENU_URI = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";
    /** 需要参数 通用access_token 和 用户openid */
    public static final String GET_FOLLEW_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";
    /** 需要参数 appID 和 回调redirect_uri 和 scope 和 标识state */
    public static final String USER_INFO_CODE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";
}
