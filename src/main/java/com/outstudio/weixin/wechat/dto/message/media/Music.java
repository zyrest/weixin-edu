package com.outstudio.weixin.wechat.dto.message.media;

/**
 * Created by 96428 on 2017/7/16.
 */
public class Music {
    private String Title;
    private String Description;
    private String MusicURL;
    private String HQMusicUrl;//高质量音乐链接，WIFI环境优先使用该链接播放音乐
    private String ThumbMediaId;//缩略图的媒体id，通过素材管理中的接口上传多媒体文件，得到的id ！必须！

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

    public String getMusicURL() {
        return MusicURL;
    }

    public void setMusicURL(String musicURL) {
        MusicURL = musicURL;
    }

    public String getHQMusicUrl() {
        return HQMusicUrl;
    }

    public void setHQMusicUrl(String HQMusicUrl) {
        this.HQMusicUrl = HQMusicUrl;
    }

    public String getThumbMediaId() {
        return ThumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        ThumbMediaId = thumbMediaId;
    }
}
