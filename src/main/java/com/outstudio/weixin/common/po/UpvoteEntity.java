package com.outstudio.weixin.common.po;

import java.io.Serializable;

public class UpvoteEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer exhibition_id;

    private String openid;

    private String ip;

    private Integer voted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExhibition_id() {
        return exhibition_id;
    }

    public void setExhibition_id(Integer exhibition_id) {
        this.exhibition_id = exhibition_id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Integer getVoted() {
        return voted;
    }

    public void setVoted(Integer voted) {
        this.voted = voted;
    }
}