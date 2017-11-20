package com.outstudio.weixin.wechat.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.common.utils.NetWorkUtil;
import com.outstudio.weixin.wechat.cache.AccessTokenCache;
import com.outstudio.weixin.wechat.config.WeixinProperties;
import com.outstudio.weixin.wechat.dto.AccessToken;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
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

    /**
     * 生成带参数的二维码
     * @param expire_seconds
     * @param action_name
     * @param scene_str
     * @return
     */
    public static JSONObject produceQRCode(String expire_seconds,
                                           String scene_str) {
        String getTicketUrl = String.format(WeixinProperties.CREATE_QRCODE, AccessTokenCache.getAccessToken().getAccess_token());

        String param = "{expire_seconds: "+expire_seconds+", action_name: QR_STR_SCENE, action_info: {scene: {scene_str: "+scene_str+"}}}";

        return NetWorkUtil.doPostUri(getTicketUrl, JSON.toJSONString(param));
    }


    /**
     * @desc ：微信上传素材的请求方法
     *
     * @param requestUrl  微信上传临时素材的接口url
     * @param file    要上传的文件
     * @return String  上传成功后，微信服务器返回的消息
     */
    public static JSONObject httpRequest(String requestUrl, File file) {
        StringBuffer buffer = new StringBuffer();

        try{
            //1.建立连接
            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  //打开链接

            //1.1输入输出设置
            httpUrlConn.setDoInput(true);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setUseCaches(false); // post方式不能使用缓存
            //1.2设置请求头信息
            httpUrlConn.setRequestProperty("Connection", "Keep-Alive");
            httpUrlConn.setRequestProperty("Charset", "UTF-8");
            //1.3设置边界
            String BOUNDARY = "----------" + System.currentTimeMillis();
            httpUrlConn.setRequestProperty("Content-Type","multipart/form-data; boundary="+ BOUNDARY);

            // 请求正文信息
            // 第一部分：
            //2.将文件头输出到微信服务器
            StringBuilder sb = new StringBuilder();
            sb.append("--"); // 必须多两道线
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"media\";filelength=\"" + file.length()
                    + "\";filename=\""+ file.getName() + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            byte[] head = sb.toString().getBytes("utf-8");
            // 获得输出流
            OutputStream outputStream = new DataOutputStream(httpUrlConn.getOutputStream());
            // 将表头写入输出流中：输出表头
            outputStream.write(head);

            //3.将文件正文部分输出到微信服务器
            // 把文件以流文件的方式 写入到微信服务器中
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                outputStream.write(bufferOut, 0, bytes);
            }
            in.close();
            //4.将结尾部分输出到微信服务器
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
            outputStream.write(foot);
            outputStream.flush();
            outputStream.close();


            //5.将微信服务器返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();


        } catch (IOException e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        return JSON.parseObject(buffer.toString());
    }


}
