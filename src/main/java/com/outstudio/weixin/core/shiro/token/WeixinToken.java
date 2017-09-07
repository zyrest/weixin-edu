package com.outstudio.weixin.core.shiro.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created by 96428 on 2017/9/6.
 * This in weixin-edu, com.outstudio.weixin.core.shiro.token
 */
public class WeixinToken implements AuthenticationToken {
    private String weixinOpenid;

    public WeixinToken(String weixinOpenid) {
        this.weixinOpenid = weixinOpenid;
    }

    public String getWeixinOpenid() {
        return weixinOpenid;
    }

    public void setWeixinOpenid(String weixinOpenid) {
        this.weixinOpenid = weixinOpenid;
    }

    @Override
    public Object getPrincipal() {
        return weixinOpenid;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
