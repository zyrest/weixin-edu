package com.outstudio.weixin.wechat.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.outstudio.weixin.common.utils.NetWorkUtil;
import com.outstudio.weixin.wechat.cache.AccessTokenCache;
import com.outstudio.weixin.wechat.config.WeixinProperties;
import com.outstudio.weixin.wechat.utils.MenuUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Created by 96428 on 2017/7/16.
 */
//@WebServlet(
//        name = "CreateMenuServlet",
//        urlPatterns = "/self/CreateMenuServlet",
//        loadOnStartup = 3
//)
public class CreateMenuServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(CreateMenuServlet.class);

    @Override
    public void init() throws ServletException {
        while (AccessTokenCache.getAccessToken().getAccess_token() == null) {
            try {
                Thread.sleep(1000 * 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.info("now start creating menus");
        super.init();
        String menuInfo = JSON.toJSONString(MenuUtil.initMenus());

        String uri = String.format(WeixinProperties.CREATE_MENU_URI,
                AccessTokenCache.getAccessToken().getAccess_token());

        JSONObject response = NetWorkUtil.doPostUriReturnJson(uri, menuInfo);

        logger.info("create menu status : " + response.toJSONString());
    }
}
