package com.outstudio.weixin.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.outstudio.weixin.wechat.config.WeixinProperties;
import com.outstudio.weixin.wechat.dto.AccessToken;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by 96428 on 2017/7/16.
 */
public class NetWorkUtil {

    private static Logger logger = Logger.getLogger(NetWorkUtil.class);

    public static AccessToken getAccessTokenByRequest() {
        JSONObject object = doGetUri(WeixinProperties.ACCESS_TOKEN_URI);
        return JSON.parseObject(object.toJSONString(), AccessToken.class);
    }

    public static JSONObject doGetUri(String uri) {
        logger.info("do get the " + uri);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(uri);
        //设置请求配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)   //设置连接超时时间
                .setConnectionRequestTimeout(5000) // 设置请求超时时间
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)//默认允许自动重定向
                .build();
        get.setConfig(requestConfig);

        String result = "";

        try {
            CloseableHttpResponse response = httpClient.execute(get);

            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity());
                logger.info("got response : " + result);
            } else {
                logger.info("something wrong happened");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            logger.info("the response is not JSON");
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return JSON.parseObject(result);
    }

    public static JSONObject doPostUri(String uri, String params) {
        logger.info("do get the " + uri);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(uri);
        //设置请求配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)   //设置连接超时时间
                .setConnectionRequestTimeout(5000) // 设置请求超时时间
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)//默认允许自动重定向
                .build();
        post.setConfig(requestConfig);

        String result = "";

        try {
            post.setEntity(new StringEntity(params, "UTF-8"));
            CloseableHttpResponse response = httpClient.execute(post);

            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity());
                logger.info("got response : " + result);
            } else {
                logger.info("something wrong happened");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            logger.info("the response is not JSON");
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return JSON.parseObject(result);
    }
}
