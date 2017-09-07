package com.outstudio.weixin.wechat.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.common.utils.NetWorkUtil;
import com.outstudio.weixin.wechat.cache.AccessTokenCache;
import com.outstudio.weixin.wechat.config.WeixinProperties;
import com.outstudio.weixin.wechat.dto.AccessToken;

/**
 * Created by 96428 on 2017/9/7.
 * This in weixin-edu, com.outstudio.weixin.wechat.utils
 */
public class WechatUtil {

    public static AccessToken getAccessTokenByRequest() {
        String uri = String.format(WeixinProperties.ACCESS_TOKEN_URI, WeixinProperties.appID, WeixinProperties.appsecret);
        JSONObject object = NetWorkUtil.doGetUri(uri);
        return JSON.parseObject(object.toJSONString(), AccessToken.class);
    }

    public static UserEntity getUserInfoOnSubscribe(String openid) {
        String uri = String.format(WeixinProperties.GET_FOLLEW_USER_INFO,
                AccessTokenCache.getAccessToken().getAccess_token(), openid);
        JSONObject json = NetWorkUtil.doGetUri(uri);
        return JSON.parseObject(json.toJSONString(), UserEntity.class);
    }
}
