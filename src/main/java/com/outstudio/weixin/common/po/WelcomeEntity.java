package com.outstudio.weixin.common.po;

import java.io.Serializable;

/**
 * Created by lmy on 2017/9/14.
 */
public class WelcomeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String content;
    private Integer isUsing;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsUsing() {
        return isUsing;
    }

    public void setIsUsing(Integer isUsing) {
        this.isUsing = isUsing;
    }
}
