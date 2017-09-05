package com.outstudio.weixin.wechat.dto.message;
import com.outstudio.weixin.wechat.dto.message.media.Voice;

/**
 * Created by 96428 on 2017/7/16.
 */
public class VoiceMessage extends BaseMessage {
    private Voice Voice;

    public Voice getVoice() {
        return Voice;
    }

    public void setVoice(Voice voice) {
        this.Voice = voice;
    }
}
