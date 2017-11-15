package com.outstudio.weixin.wechat.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.outstudio.weixin.common.po.WelcomeEntity;
import com.outstudio.weixin.common.service.WelcomeService;
import com.outstudio.weixin.wechat.dto.message.media.Item;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 96428 on 2017/7/14.
 */
@Service
public class ContentUtil {

    @Resource
    private WelcomeService welcomeService;

    public static String hello1() {
        StringBuilder s = new StringBuilder();
        s.append("你好啊!\n\n");
        s.append("这是我的测试账号\n\n");
        s.append("输入的指令正确，指令为 1");

        return s.toString();
    }

    public String onSubscribe(String nickname) {

        StringBuilder s = new StringBuilder();
        s.append(nickname);
        s.append("~");
        s.append("你好啊!\n\n");
        s.append("欢迎您的关注\n\n");
        WelcomeEntity welcomeEntity = welcomeService.getByIsUsing(1);
        if (welcomeEntity == null) {
            return s.toString();
        }else {
            return s.append(welcomeService.getByIsUsing(1).getContent().replace("\\n", "\n")).toString();
        }
    }

    public List<Item> news() {
        JSONObject materialListobject = WechatUtil.getMaterialList("news", "0", "1");
        JSONArray itemArray = materialListobject.getJSONArray("item");

        List<Item> items = new LinkedList<>();

//        for (int i = 0;i<itemArray.size();i++) {
            JSONObject itemObject = (JSONObject) itemArray.get(0);
            String media_id = itemObject.getString("media_id");
            JSONObject news_item = itemObject.getJSONObject("content");
            JSONArray newsItemArray = news_item.getJSONArray("news_item");

            for (int j = 0;j<newsItemArray.size();j++) {
                JSONObject newsItemObject = (JSONObject) newsItemArray.get(j);
                Item item = new Item();
                item.setTitle(newsItemObject.getString("title"));
                item.setPicUrl(newsItemObject.getString("url"));
                item.setUrl(newsItemObject.getString("content_source_url"));
                item.setDescription("");
                items.add(item);
            }
//        }
        return items;
    }
}
