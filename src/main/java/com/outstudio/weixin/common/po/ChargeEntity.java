package com.outstudio.weixin.common.po;

import java.util.Date;

public class ChargeEntity {
    private Integer id;

    private String openid;

    private Date now_date;

    private Integer dates;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public Date getNow_date() {
        return now_date;
    }

    public void setNow_date(Date now_date) {
        this.now_date = now_date;
    }

    public Integer getDates() {
        return dates;
    }

    public void setDates(Integer dates) {
        this.dates = dates;
    }
}