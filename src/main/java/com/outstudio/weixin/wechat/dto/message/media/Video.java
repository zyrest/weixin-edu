package com.outstudio.weixin.wechat.dto.message.media;

/**
 * Created by 96428 on 2017/7/16.
 */
public class Video {
    private String Title;
    private String Description;
    private String MediaId;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
