package com.outstudio.weixin.wechat.core.router;


import com.alibaba.fastjson.JSON;
import com.outstudio.weixin.wechat.config.MessageType;
import com.outstudio.weixin.wechat.core.handler.Handler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by 96428 on 2017/7/15.
 */
@Service("normalRouter")
public class NormalRouter implements Router {

    @Resource(name = "eventRouter")
    private Router eventRouter;

    @Resource(name = "textMessageHandler")
    private Handler textMessageHandler;

    @Override
    public String dispatchMessage(Map<String, String> messageMap) {
        String result = "success";
        String messageType = messageMap.get("MsgType");
        System.out.println(JSON.toJSONString(messageMap));

        switch (messageType) {
            case (MessageType.MESSAGE_TEXT) :
                result = textMessageHandler.handler(messageMap);
                break;
            case (MessageType.MESSAGE_VOICE) :
                break;
            case (MessageType.MESSAGE_IMAGE) :
                break;
            case (MessageType.MESSAGE_VIDEO) :
                break;
            case (MessageType.MESSAGE_SHORTVIDEO) :
                break;
            case (MessageType.MESSAGE_LOCATION) :
                break;
            case (MessageType.MESSAGE_LINK) :
                break;
            case (MessageType.MESSAGE_EVENT) :
                result = eventRouter.dispatchMessage(messageMap);
                break;
            default:
                result = "success";
        }

        return result;
    }
}
