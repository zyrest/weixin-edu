package com.outstudio.weixin.wechat.core.router;


import com.outstudio.weixin.wechat.config.EventType;
import com.outstudio.weixin.wechat.core.handler.Handler;
import com.outstudio.weixin.wechat.core.handler.SubscribeHandler;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by 96428 on 2017/7/15.
 */
@Service("eventRouter")
public class EventRouter implements Router {

//    @Autowired
//    @Qualifier("subscribeHandler")
    private Handler subscribeHandler = new SubscribeHandler();

    @Override
    public String dispatchMessage(Map<String, String> messageMap) {
        String event = messageMap.get("Event");
        String result = "success";

        switch (event) {
            case (EventType.EVENT_SUBSCRIBE) :
                result = subscribeHandler.handler(messageMap);
                break;
            case (EventType.EVENT_UNSUBSCRIBE) :
                break;
            case (EventType.EVENT_SCAN) :
                break;
            case (EventType.EVENT_LOCATION) :
                break;
            case (EventType.EVENT_CLICK) :
                break;
            case (EventType.EVENT_VIEW) :
                break;
            default:
                result = "success";
        }

        return result;
    }
}
