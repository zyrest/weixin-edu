package com.outstudio.weixin.wechat.servlet;

import com.outstudio.weixin.common.utils.LoggerUtil;
import com.outstudio.weixin.wechat.cache.AccessTokenCache;
import com.outstudio.weixin.wechat.utils.WechatUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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

        new Thread(() -> {
            //进入无限循环， 持续获得Token
            while (true) {
                try {
                    //获取AccessToken
                    AccessTokenCache.setAccessToken(WechatUtil.getAccessTokenByRequest());

                    if (AccessTokenCache.getAccessToken() == null) {
                        Thread.sleep(1000 * 2);
                    } else { //正确获取token 按照过期时间判定休眠时间
                        LoggerUtil.fmtDebug(getClass(),
                                "已经正确获取AccessToken, 现在的token为->{%s}",
                                AccessTokenCache.getAccessToken().getAccess_token());
                        Integer sleepTime =
                                Integer.parseInt(AccessTokenCache.getAccessToken().getExpires_in())
                                        - 200;
                        Thread.sleep(1000 * sleepTime);
                    }
                } catch (Exception e) {
                    //处理异常 休眠1秒
                    LoggerUtil.fmtError(getClass(), e,
                            "获取AccessToken出现错误, 错误信息为->{%s}", e.getMessage());

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    //处理异常结束
                }
            }
            //无限循环结束（不会结束
        }).start();
    }
}
