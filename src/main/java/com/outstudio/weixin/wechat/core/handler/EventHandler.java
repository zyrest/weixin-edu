package com.outstudio.weixin.wechat.core.handler;


import com.outstudio.weixin.wechat.config.EventType;
import com.outstudio.weixin.wechat.utils.ContentUtil;
import com.outstudio.weixin.wechat.utils.MessageUtil;

import java.util.Map;

/**
 * Created by 96428 on 2017/7/15.
 */
public class EventHandler implements Handler {

    private Map<String, String> messageMap;

    @Override
    public String dispatchMessage(Map<String, String> messageMap) {
        this.messageMap = messageMap;

        String event = messageMap.get("Event");
        String result = "success";

        switch (event) {
            case (EventType.EVENT_SUBSCRIBE) :
                result = handleSubscribeEvent();
                break;
            case (EventType.EVENT_UNSUBSCRIBE) :
                result = handleUnsubscribeEvent();
                break;
            case (EventType.EVENT_SCAN) :
                result = handleScanEvent();
                break;
            case (EventType.EVENT_LOCATION) :
                result = handleLocationEvent();
                break;
            case (EventType.EVENT_CLICK) :
                result = handleClickEvent();
                break;
            case (EventType.EVENT_VIEW) :
                result = handleViewEvent();
                break;
        }

        return result;
    }

    private String handleSubscribeEvent() {
        String fromUser = messageMap.get("ToUserName");
        String toUser = messageMap.get("FromUserName");

        return MessageUtil.createTextMessageXml(fromUser, toUser, ContentUtil.onSubscribe());
    }

    private String handleUnsubscribeEvent() {

        return null;
    }

    private String handleScanEvent() {

        return null;
    }

    private String handleLocationEvent() {

        return null;
    }

    private String handleClickEvent() {
        String eventKey = messageMap.get("EventKey");
        String fromUser = messageMap.get("ToUserName");
        String toUser = messageMap.get("FromUserName");

        logger.info("event key is : " + eventKey);
        String result = "success";

        if (eventKey.equals("1")) {
            result = MessageUtil.createTextMessageXml(fromUser, toUser, ContentUtil.hello1());
        }

        return result;
    }

    private String handleViewEvent() {

        return null;
    }
}
