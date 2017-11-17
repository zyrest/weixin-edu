package com.outstudio.weixin.wechat.config;

/**
 * Created by 96428 on 2017/7/16.
 */
public class WeixinProperties {

    public static final String DOMAIN = "http://www.here52.cn";

    public static final String TOKEN = "wechat_edu";
    public static final String appID = "wx6b2cfecab5b48621";
    public static final String appSecret = "48a1796bdca284377046f40423da162e";

    //测试
//    public static final String appID = "wxd53def0a24d17b53";
//    public static final String appSecret = "14d2831e0d8153867040d95674808d40";

    public static final String mchID = "1491353082";//商户id
    public static final String appKey = "2ab9071b06b9f739b950ddb41db2690d";

    public static final String BASE_SCOPE = "snsapi_base";
    public static final String USERINFO_SCOPE = "snsapi_userinfo";
    /** 需要参数 appID 和 appSecret */
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

    /**
     * 网页授权的时候USER_INFO_CODE中的state标识
     */
    public static final String OAUTH_STATE = "state";

    /**
     * 格式化的网页授权url
     */
    public static final String FORMATED_USER_INFO_CODE = String.format(USER_INFO_CODE, appID, OAUTH_REDIRECT_URL, USERINFO_SCOPE, OAUTH_STATE);


    /**
     * 获取素材列表api
     */
    public static final String GET_MATERIAL_LIST_URL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=%s";

    /**
     * 根据media id获取永久素材api
     */
    public static final String GET_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=%s";

    /**
     * 新增临时素材
     */
    public static final String UPLOAD_TEMPORARY_MATERIAL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=%s&type=%s";
}
