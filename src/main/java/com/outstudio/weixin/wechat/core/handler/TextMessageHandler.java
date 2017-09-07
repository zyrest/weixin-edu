package com.outstudio.weixin.wechat.core.handler;

import com.outstudio.weixin.common.utils.LoggerUtil;
import com.outstudio.weixin.wechat.dto.message.media.Item;
import com.outstudio.weixin.wechat.utils.ContentUtil;
import com.outstudio.weixin.wechat.utils.MessageUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 96428 on 2017/9/7.
 * This in weixin-edu, com.outstudio.weixin.wechat.core.handler
 */
@Service("textMessageHandler")
public class TextMessageHandler implements Handler {

    @Override
    public String handler(Map<String, String> messageMap) {
        String content = messageMap.get("Content");
        String fromUser = messageMap.get("ToUserName");
        String toUser = messageMap.get("FromUserName");

        LoggerUtil.fmtDebug(getClass(), "用户输入内容是->{%s}", content);
        String result = "success";

        if (content.equals("1")) {
            result = MessageUtil.createTextMessageXml(fromUser, toUser, ContentUtil.hello1());
        } else if (content.equals("2")) {
            List<Item> items = new ArrayList<>();

            Item one = new Item();
            one.setTitle("测试内容1");
            one.setDescription("csdn博客");
            one.setUrl("http://blog.csdn.net/");
            one.setPicUrl("http://ss.csdn.net/p?http://mmbiz.qpic.cn/mmbiz_jpg/1hReHaqafaflH9mgsL0dialsMGWcxxEXORvRwqKGxCxgxXq5OickOWdRB3DJQNjTnFQ7O2iasQFQMhaaIMGecU1Zw/640?wx_fmt=jpeg" );

            Item two = new Item();
            two.setTitle("测试内容2");
            two.setDescription("还是csdn博客");
            two.setUrl("http://blog.csdn.net/");
            two.setPicUrl("http://ss.csdn.net/p?http://mmbiz.qpic.cn/mmbiz_jpg/1hReHaqafaflH9mgsL0dialsMGWcxxEXORvRwqKGxCxgxXq5OickOWdRB3DJQNjTnFQ7O2iasQFQMhaaIMGecU1Zw/640?wx_fmt=jpeg" );

            items.add(one);
            items.add(two);

            result = MessageUtil.createArticlesMessageXml(fromUser, toUser, items);
        }

        return result;
    }
}
