package com.outstudio.weixin.wechat.dto.message;

import com.outstudio.weixin.wechat.dto.message.media.Video;

/**
 * Created by 96428 on 2017/7/16.
 */
public class VideoMessage extends BaseMessage {
    private Video Video;

    public Video getVideo() {
        return Video;
    }

    public void setVideo(Video video) {
        this.Video = video;
    }
}
