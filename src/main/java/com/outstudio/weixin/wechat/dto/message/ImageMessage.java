package com.outstudio.weixin.wechat.dto.message;

import com.outstudio.weixin.wechat.dto.message.media.Image;

/**
 * Created by 96428 on 2017/7/16.
 */
public class ImageMessage extends BaseMessage {
    private Image Image;

    public Image getImage() {
        return Image;
    }

    public void setImage(Image image) {
        Image = image;
    }
}
