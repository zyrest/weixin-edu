package com.outstudio.weixin.wechat.core;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 96428 on 2017/7/15.
 */
public class MyWechatFactory {

    private static MyWechat myWechat = new MyWechat();

    public static MyWechat getMyWechat(HttpServletRequest request) {
        myWechat.setRequest(request);

        return myWechat;
    }

    private MyWechatFactory(){}
}
