package com.outstudio.weixin.common.po;

import java.io.Serializable;
import java.util.Date;

public class EnVideoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String title;

    private Integer stage;

    private String src;

    private Integer is_free;

    private Date post_date;

    private String description;

    private String fileid;

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

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

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src == null ? null : src.trim();
    }

    public Integer getIs_free() {
        return is_free;
    }

    public void setIs_free(Integer is_free) {
        this.is_free = is_free;
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
}