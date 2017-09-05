package com.outstudio.weixin.wechat.dto.message;

import com.outstudio.weixin.wechat.dto.message.media.Music;

/**
 * Created by 96428 on 2017/7/16.
 */
public class MusicMessage extends BaseMessage {
    private Music Music;

    public Music getMusic() {
        return Music;
    }

    public void setMusic(Music music) {
        Music = music;
    }
}
