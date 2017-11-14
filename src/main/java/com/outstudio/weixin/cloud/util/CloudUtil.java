package com.outstudio.weixin.cloud.util;

import com.alibaba.fastjson.JSONObject;
import com.outstudio.weixin.cloud.properties.VodProperties;
import com.outstudio.weixin.common.utils.LoggerUtil;
import com.outstudio.weixin.common.utils.NetWorkUtil;
import com.qcloud.Common.Request;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Random;
import java.util.TreeMap;

/**
 * 云视频管理utility
 */
public class CloudUtil {

    private static final String HMAC_ALGORITHM = "HmacSHA1";
    private static final String CONTENT_CHARSET = "UTF-8";
    private static final long signValidDuration = 3600 * 24 * 2;


    public static JSONObject deleteVodFile(String fileId,
                                     int isFlushCdn,
                                     Integer priority) {
        TreeMap<String, Object> params = new TreeMap<>();
        params.put("Action", "DeleteVodFile");
        params.put("fileId", fileId);
        params.put("priority", priority);
        params.put("isFlushCdn", isFlushCdn);
        params.put("SignatureMethod", "HmacSHA256");
//        params.put("Region", "ap-guangzhou-open");
        params.put("SecretId", VodProperties.secretId);

        LoggerUtil.fmtDebug(CloudUtil.class, "删除云视频，请求参数为{%s}", params.toString());
        String url=Request.generateUrl(params, VodProperties.secretId, VodProperties.secretKey, "GET", "vod.api.qcloud.com", "/v2/index.php");
        LoggerUtil.fmtDebug(CloudUtil.class, "删除云视频，请求url为{%s}", url);

        JSONObject object = NetWorkUtil.doGetUri(url);
        LoggerUtil.fmtDebug(CloudUtil.class,"返回参数为：{%s}",object.toString());
        return object;
    }

    public static JSONObject  deleteVodFile(String fileId, Integer priority) {
        TreeMap<String, Object> params = new TreeMap<>();
        params.put("Action", "DeleteVodFile");
        params.put("fileId", fileId);
        params.put("priority", priority);
        params.put("SignatureMethod", "HmacSHA256");
//        params.put("Region", "ap-guangzhou-open");
        params.put("SecretId", VodProperties.secretId);

        LoggerUtil.fmtDebug(CloudUtil.class, "删除云视频，请求参数为{%s}", params.toString());
        String url=Request.generateUrl(params, VodProperties.secretId, VodProperties.secretKey, "GET", "vod.api.qcloud.com", "/v2/index.php");
        LoggerUtil.fmtDebug(CloudUtil.class, "删除云视频，请求url为{%s}", url);

        JSONObject object = NetWorkUtil.doGetUri(url);
        LoggerUtil.fmtDebug(CloudUtil.class,"返回参数为：{%s}",object.toString());
        return object;
    }

    public static JSONObject deleteVodFile(String fileId) {
        return deleteVodFile(fileId, 0);
    }

    public static String getUploadSignature() throws Exception {
        String strSign = "";
        String contextStr = "";

        long endTime = (System.currentTimeMillis() / 1000 + signValidDuration);
        contextStr += "secretId=" + java.net.URLEncoder.encode(VodProperties.secretId, "utf8");
        contextStr += "&currentTimeStamp=" + System.currentTimeMillis() / 1000;
        contextStr += "&expireTime=" + endTime;
        contextStr += "&random=" + new Random().nextInt(java.lang.Integer.MAX_VALUE);

        Mac mac = Mac.getInstance(HMAC_ALGORITHM);
        SecretKeySpec secretKey = new SecretKeySpec(VodProperties.secretKey.getBytes(CONTENT_CHARSET), mac.getAlgorithm());
        mac.init(secretKey);

        byte[] hash = mac.doFinal(contextStr.getBytes(CONTENT_CHARSET));
        byte[] sigBuf = byteMerger(hash, contextStr.getBytes("utf8"));
        strSign = new String(new BASE64Encoder().encode(sigBuf).getBytes());
        strSign = strSign.replace(" ", "").replace("\n", "").replace("\r", "");
        return strSign;
    }

    private static byte[] byteMerger(byte[] byte1, byte[] byte2) {
        byte[] byte3 = new byte[byte1.length + byte2.length];
        System.arraycopy(byte1, 0, byte3, 0, byte1.length);
        System.arraycopy(byte2, 0, byte3, byte1.length, byte2.length);
        return byte3;
    }
}
