package com.outstudio.weixin.core.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by 96428 on 2017/9/6.
 * This in weixin-edu, com.outstudio.weixin.core.shiro.token
 */
public class WeixinToken extends UsernamePasswordToken {
    private String weixinOpenid;
    private String account;
    private String pass;
    private boolean rememberMe;
    private boolean isWechat;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean isWechat() {
        return isWechat;
    }

    public void setWechat(boolean wechat) {
        isWechat = wechat;
    }

    public String getWeixinOpenid() {
        return weixinOpenid;
    }

    public void setWeixinOpenid(String weixinOpenid) {
        this.weixinOpenid = weixinOpenid;
    }

    @Override
    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    @Override
    public Object getPrincipal() {
        return account;
    }

    @Override
    public Object getCredentials() {
        return pass;
    }
}
