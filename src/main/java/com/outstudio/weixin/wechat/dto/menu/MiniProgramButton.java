package com.outstudio.weixin.wechat.dto.menu;

/**
 * Created by 96428 on 2017/7/16.
 */
public class MiniProgramButton extends Button {
    private String url;//不支持小程序的老版本客户端将打开本url。
    private String appid;
    private String pagepath;//小程序的页面路径

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPagepath() {
        return pagepath;
    }

    public void setPagepath(String pagepath) {
        this.pagepath = pagepath;
    }
}
