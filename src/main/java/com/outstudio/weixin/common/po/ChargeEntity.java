package com.outstudio.weixin.common.po;

import java.io.Serializable;
import java.util.Date;

public class ChargeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String openid;

    private Date now_date;

    private Integer dates;

    private String transaction_id;

    private String out_trade_no;

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

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