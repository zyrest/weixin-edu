package com.outstudio.weixin.wechat.core.handler;


import com.outstudio.weixin.wechat.config.MessageType;
import com.outstudio.weixin.wechat.dto.message.media.Item;
import com.outstudio.weixin.wechat.utils.ContentUtil;
import com.outstudio.weixin.wechat.utils.MessageUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 96428 on 2017/7/15.
 */
public class NormalHandler implements Handler {

    private Handler handler = new EventHandler();

    private Map<String, String> messageMap;

    @Override
    public String dispatchMessage(Map<String, String> messageMap) {
        this.messageMap = messageMap;

        String result = "success";
        String messageType = messageMap.get("MsgType");

        switch (messageType) {
            case (MessageType.MESSAGE_TEXT) :
                result = handleTextMessage();
                break;
            case (MessageType.MESSAGE_VOICE) :
                result = handleVoiceMessage();
                break;
            case (MessageType.MESSAGE_IMAGE) :
                result = handleImageMessage();
                break;
            case (MessageType.MESSAGE_VIDEO) :
                result = handleVideoMessage();
                break;
            case (MessageType.MESSAGE_SHORTVIDEO) :
                result = handleShortvideoMessage();
                break;
            case (MessageType.MESSAGE_LOCATION) :
                result = handleLocationMessage();
                break;
            case (MessageType.MESSAGE_LINK) :
                result = handleLinkMessage();
                break;
            case (MessageType.MESSAGE_EVENT) :
                result = handleEventMessage();
                break;
        }

        return result;
    }
    
    private String handleTextMessage() {
        String content = messageMap.get("Content");
        String fromUser = messageMap.get("ToUserName");
        String toUser = messageMap.get("FromUserName");

        logger.info("user input " + content);
        String result = "success";

        if (content.equals("1")) {
            result = MessageUtil.createTextMessageXml(fromUser, toUser, ContentUtil.hello1());
        } else if (content.equals("2")) {
            List<Item> items = new ArrayList<>();

            Item one = new Item();
            one.setTitle("震惊！他竟然做出这种事！");
            one.setDescription("xxxxxxxxx");
            one.setUrl("http://blog.csdn.net/");
            one.setPicUrl("http://ss.csdn.net/p?http://mmbiz.qpic.cn/mmbiz_jpg/1hReHaqafaflH9mgsL0dialsMGWcxxEXORvRwqKGxCxgxXq5OickOWdRB3DJQNjTnFQ7O2iasQFQMhaaIMGecU1Zw/640?wx_fmt=jpeg" );

            Item two = new Item();
            two.setTitle("!!!!!!!!!AAAAAAAA");
            two.setDescription("xxxxxxxxx");
            two.setUrl("http://blog.csdn.net/");
            two.setPicUrl("http://ss.csdn.net/p?http://mmbiz.qpic.cn/mmbiz_jpg/1hReHaqafaflH9mgsL0dialsMGWcxxEXORvRwqKGxCxgxXq5OickOWdRB3DJQNjTnFQ7O2iasQFQMhaaIMGecU1Zw/640?wx_fmt=jpeg" );

            items.add(one);
            items.add(two);

            result = MessageUtil.createArticlesMessageXml(fromUser, toUser, items);
        }

        return result;
    }

    private String handleImageMessage() {

        return null;
    }

    private String handleVoiceMessage() {

        return null;
    }

    private String handleVideoMessage() {

        return null;
    }

    private String handleShortvideoMessage() {

        return null;
    }

    private String handleLocationMessage() {

        return null;
    }

    private String handleLinkMessage() {

        return null;
    }

    private String handleEventMessage() {

        return handler.dispatchMessage(messageMap);
    }
}
