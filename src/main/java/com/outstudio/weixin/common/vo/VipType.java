package com.outstudio.weixin.common.vo;

/**
 * Created by lmy on 2018/2/14.
 */
public enum VipType {

    ENGLISH("english"), MATH("math"), PHYSICS("physics"), CHEMISTRY("chemistry");

    private String name;

    VipType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
