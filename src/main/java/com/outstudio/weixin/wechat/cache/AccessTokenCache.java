package com.outstudio.weixin.wechat.cache;


import com.outstudio.weixin.wechat.dto.AccessToken;

/**
 * Created by 96428 on 2017/7/16.
 */
public class AccessTokenCache {
    private static AccessToken accessToken = new AccessToken();

    public static AccessToken getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(AccessToken accessToken) {
        AccessTokenCache.accessToken = accessToken;
    }
}
