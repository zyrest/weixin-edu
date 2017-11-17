package com.outstudio.weixin.wechat.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.common.utils.NetWorkUtil;
import com.outstudio.weixin.wechat.cache.AccessTokenCache;
import com.outstudio.weixin.wechat.config.WeixinProperties;
import com.outstudio.weixin.wechat.dto.AccessToken;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 96428 on 2017/9/7.
 * This in weixin-edu, com.outstudio.weixin.wechat.utils
 */
public class WechatUtil {

    public static AccessToken getAccessTokenByRequest() {
        String uri = String.format(WeixinProperties.ACCESS_TOKEN_URI, WeixinProperties.appID, WeixinProperties.appSecret);
        JSONObject object = NetWorkUtil.doGetUri(uri);
        return JSON.parseObject(object.toJSONString(), AccessToken.class);
    }

    /*
    根据用户openid获取用户信息
     */
    public static UserEntity getUserInfoOnSubscribe(String openid) {
        String uri = String.format(WeixinProperties.GET_FOLLEW_USER_INFO,
                AccessTokenCache.getAccessToken().getAccess_token(), openid);
        JSONObject json = NetWorkUtil.doGetUri(uri);
        return JSON.parseObject(json.toJSONString(), UserEntity.class);
    }

    /*
    获取公众号素材列表
     */
    public static JSONObject getMaterialList(String type, String offset, String count) {
        String url = String.format(WeixinProperties.GET_MATERIAL_LIST_URL,
                AccessTokenCache.getAccessToken().getAccess_token());
        Map<String, String> params = new HashMap<>();
        params.put("type", type);
        params.put("offset", offset);
        params.put("count", count);
        String jsonParams = JSON.toJSONString(params);
        return NetWorkUtil.doPostUri(url, jsonParams);
    }

    /*
    获取素材列表中的素材
     */
    public static JSONObject getMaterial(String media_id) {
        String url = String.format(WeixinProperties.GET_MATERIAL_URL,
                AccessTokenCache.getAccessToken().getAccess_token());
        Map<String, String> params = new HashMap<>();
        params.put("access_token", AccessTokenCache.getAccessToken().getAccess_token());
        params.put("media_id", media_id);
        String jsonParams = JSON.toJSONString(params);
        return NetWorkUtil.doPostUri(url, jsonParams);
    }

    /**
     * 上传临时素材
     * @param localFilePath
     * @return
     */
    public static JSONObject uploadTemporaryMaterial(String localFilePath,String type) {
        String url = String.format(WeixinProperties.UPLOAD_TEMPORARY_MATERIAL,
                AccessTokenCache.getAccessToken().getAccess_token(),
                type);
        return NetWorkUtil.uploadFile(url, localFilePath, "media");
    }

}
