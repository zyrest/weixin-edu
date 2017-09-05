package com.outstudio.weixin.common.vo;

import java.util.Date;

/**
 * Created by 96428 on 2017/7/17.
 * This in TestWeixin, samson.back.dto
 */
public class MessageVo {
    private Integer status;
    private String message;
    private Object data;
    private Date time;

    public Integer getStatus() {
        return status;
    }

    public MessageVo setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public MessageVo setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public MessageVo setData(Object data) {
        this.data = data;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public MessageVo setTime(Date time) {
        this.time = time;
        return this;
    }
}
