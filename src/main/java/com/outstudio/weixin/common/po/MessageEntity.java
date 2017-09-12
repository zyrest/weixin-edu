package com.outstudio.weixin.common.po;

import java.io.Serializable;
import java.util.Date;

public class MessageEntity implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String title;

    private Integer state;

    private Date post_date;

    private Date last_modify_date;

    private String description;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getPost_date() {
        return post_date;
    }

    public void setPost_date(Date post_date) {
        this.post_date = post_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getLast_modify_date() {
        return last_modify_date;
    }

    public void setLast_modify_date(Date last_modify_date) {
        this.last_modify_date = last_modify_date;
    }

}