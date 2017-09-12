package com.outstudio.weixin.wechat.core.router;


import com.alibaba.fastjson.JSON;
import com.outstudio.weixin.wechat.config.MessageType;
import com.outstudio.weixin.wechat.core.handler.Handler;
import com.outstudio.weixin.wechat.core.handler.TextMessageHandler;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by 96428 on 2017/7/15.
 */
@Service("normalRouter")
public class NormalRouter implements Router {

//    @Autowired
//    @Qualifier("eventRouter")
    private Router eventRouter = new EventRouter();

//    @Autowired
//    @Qualifier("textMessageHandler")
    private Handler textMessageHandler = new TextMessageHandler();

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
