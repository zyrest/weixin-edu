package com.outstudio.weixin.wechat.config;

/**
 * Created by 96428 on 2017/7/16.
 */
public class WeixinProperties {

    public static final String TOKEN = "samson";
//    public static final String appID = "wxd53def0a24d17b53";
//    public static final String appsecret = "14d2831e0d8153867040d95674808d40";

    //刘明宇的测试号
    public static final String appID = "wxc6d57f9289e2394b";
    public static final String appsecret = "1c2bb4315e2ad986392b7c93d79cda1f";

    public static final String BASE_SCOPE = "snsapi_base";
    public static final String USERINFO_SCOPE = "snsapi_userinfo";
    /** 需要参数 appID 和 appsecret */
    public static final String ACCESS_TOKEN_URI = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    /** 需要参数 通用access_token */
    public static final String CREATE_MENU_URI = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";
    /** 需要参数 通用access_token 和 用户openid */
    public static final String GET_FOLLEW_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";
    /**
     * 需要参数 appID 和 回调redirect_uri 和 scope 和 标识state
     */
    public static final String USER_INFO_CODE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";

    /**
     * 获取oauth认证的access_token，和通用的access_token不一样
     */
    public static final String OAUTH_ACCESS_TOKEN_URI = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    /**
     * 刷新oauth的access_token
     */
    public static final String REFRESH_OAUTH_ACCESS_TOKEN_URI = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s";

    /**
     * 通过oauth access_token获取用户信息
     */
    public static final String OAUTH_USER_INFO_URI = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

    /**
     * 网页授权的时候跳转的url
     */
    public static final String OAUTH_REDIRECT_URL = "http://xxxx/open/page/oauth";


}
