package com.outstudio.weixin.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.wechat.config.WeixinProperties;
import com.outstudio.weixin.wechat.dto.OAuthAccessToken;

/**
 * Created by lmy on 2017/9/13.
 */
public class OAuthUtil {

    public static OAuthAccessToken getOAuthAccessToken(String code) {
        String uri = String.format(WeixinProperties.OAUTH_ACCESS_TOKEN_URI, WeixinProperties.appID, WeixinProperties.appsecret, code);
        JSONObject object = NetWorkUtil.doGetUri(uri);
        return JSON.parseObject(object.toJSONString(), OAuthAccessToken.class);
    }

    public static UserEntity getUserInfo(OAuthAccessToken authAccessToken) {
        String uri = String.format(WeixinProperties.OAUTH_USER_INFO_URI, authAccessToken.getAccess_token(), authAccessToken.getOpenid());
        JSONObject object = NetWorkUtil.doGetUri(uri);
        return JSON.parseObject(object.toJSONString(), UserEntity.class);
    }

    public static OAuthAccessToken refreshOAuthAccessToken(OAuthAccessToken authAccessToken) {
        String uri = String.format(WeixinProperties.REFRESH_OAUTH_ACCESS_TOKEN_URI, WeixinProperties.appID, authAccessToken.getRefresh_token());
        JSONObject object = NetWorkUtil.doGetUri(uri);
        return JSON.parseObject(object.toJSONString(), OAuthAccessToken.class);
    }
}
