package com.outstudio.weixin.wechat.servlet;

import com.outstudio.weixin.common.utils.NetWorkUtil;
import com.outstudio.weixin.wechat.cache.AccessTokenCache;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by 96428 on 2017/7/16.
 */
//@WebServlet(
//        name = "AccessTokenServlet",
//        urlPatterns = "/self/AccessTokenServlet",
//        loadOnStartup = 1
//)
public class AccessTokenServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(AccessTokenServlet.class);

    @Override
    public void init() throws ServletException {
        logger.info("服务已经启动，正在加载AccessTokenServlet");
        super.init();

        new Thread(new Runnable() {

            @Override
            public void run() {
                //进入无限循环， 持续获得Token
                while (true) {
                    try {
                        //获取AccessToken
                        AccessTokenCache.setAccessToken(NetWorkUtil.getAccessTokenByRequest());

                        if (AccessTokenCache.getAccessToken() == null) {
                            Thread.sleep(1000 * 2);
                        } else { //正确获取token 按照过期时间判定休眠时间
                            logger.info("now token is : " + AccessTokenCache.getAccessToken().getAccess_token());
                            Integer sleepTime =
                                    Integer.parseInt(AccessTokenCache.getAccessToken().getExpires_in())
                                            - 200;
                            Thread.sleep(1000 * sleepTime);
                        }
                    } catch (Exception e) {
                        //处理异常 休眠1秒
                        logger.info("there is an exception : " + e.getMessage());
                        e.printStackTrace();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        //处理异常结束
                    }
                }
                //无限循环结束（不会结束
            }
        }).start();
    }
}
