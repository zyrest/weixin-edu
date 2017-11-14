package com.outstudio.weixin.cloud.util;

import com.alibaba.fastjson.JSONObject;
import com.outstudio.weixin.cloud.properties.VodProperties;
import com.outstudio.weixin.common.utils.LoggerUtil;
import com.outstudio.weixin.common.utils.NetWorkUtil;
import com.qcloud.Common.Request;

import java.util.TreeMap;

/**
 * 云视频管理utility
 */
public class CloudUtil {

    public static void deleteVodFile(String fileId,
                                     int isFlushCdn,
                                     Integer priority) {
        TreeMap<String, Object> params = new TreeMap<>();
        params.put("Action", "DeleteVodFile");
        params.put("fileId", fileId);
        params.put("priority", priority);
        params.put("isFlushCdn", isFlushCdn);
//        params.put("Region", "ap-guangzhou-open");
        params.put("SecretId", VodProperties.secretId);

        LoggerUtil.fmtDebug(CloudUtil.class, "删除云视频，请求参数为{%s}", params.toString());
        String url=Request.generateUrl(params, VodProperties.secretId, VodProperties.secretKey, "GET", "vod.api.qcloud.com", "/v2/index.php");
        LoggerUtil.fmtDebug(CloudUtil.class, "删除云视频，请求url为{%s}", url);

        NetWorkUtil.doGetUri(url);
    }

    public static void  deleteVodFile(String fileId, Integer priority) {
        TreeMap<String, Object> params = new TreeMap<>();
        params.put("Action", "DeleteVodFile");
        params.put("fileId", fileId);
        params.put("priority", priority);
        params.put("Region", "ap-guangzhou-open");
        params.put("SecretId", VodProperties.secretId);

        LoggerUtil.fmtDebug(CloudUtil.class, "删除云视频，请求参数为{%s}", params.toString());
        String url=Request.generateUrl(params, VodProperties.secretId, VodProperties.secretKey, "GET", "vod.api.qcloud.com", "/v2/index.php");
        LoggerUtil.fmtDebug(CloudUtil.class, "删除云视频，请求url为{%s}", url);

        JSONObject object = NetWorkUtil.doGetUri(url);
        Integer returnCode = object.getInteger("code");
        String returnMessage = object.getString("message");
    }

    public static void deleteVodFile(String fileId) {
        deleteVodFile(fileId, 0);
    }
}
