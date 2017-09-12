package com.outstudio.weixin.common.utils;

import com.outstudio.weixin.common.consts.ResponseStatus;
import com.outstudio.weixin.common.vo.MessageVo;

/**
 * Created by 96428 on 2017/9/12.
 * This in weixin-edu, com.outstudio.weixin.common.utils
 */
public class MessageVoUtil {
    public static MessageVo success() {
        return new MessageVo().setStatus(ResponseStatus.SUCCESS).setMessage("success");
    }

    public static MessageVo success(Object data) {
        MessageVo messageVo = new MessageVo();
        messageVo.setStatus(ResponseStatus.SUCCESS).setMessage("success").setData(data);
        return messageVo;
    }
}
