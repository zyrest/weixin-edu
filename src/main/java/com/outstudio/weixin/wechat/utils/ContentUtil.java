package com.outstudio.weixin.wechat.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.outstudio.weixin.common.po.WelcomeEntity;
import com.outstudio.weixin.common.service.WelcomeService;
import com.outstudio.weixin.common.utils.LoggerUtil;
import com.outstudio.weixin.wechat.config.WeixinProperties;
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

    public String onScan() {
        StringBuilder s = new StringBuilder();
        s.append("龙跃科技是一家专注于儿童教育的公司 \n");
        s.append("<a href='" + WeixinProperties.DOMAIN + "/page/view/introduction" + "'>点我了解更多</a> \n");
        s.append("<a href='" + WeixinProperties.DOMAIN + "/page/view/vip" + "'>购买会员，享受更多权利</a> \n");

        return s.toString();
    }

    public String onHasPidScan() {
        StringBuilder s = new StringBuilder();
        s.append("您已经填写了邀请码 \n");
        s.append("<a href='" + WeixinProperties.DOMAIN + "/page/view/introduction" + "'>点我了解更多</a> \n");
        s.append("<a href='" + WeixinProperties.DOMAIN + "/page/view/vip" + "'>购买会员，享受更多权利</a> \n");

        return s.toString();
    }

    public String onScanDenied() {
        StringBuilder s = new StringBuilder();
        s.append("不可以扫自己的推广二维码的哦~~~ \n");
        s.append("<a href='" + WeixinProperties.DOMAIN + "/page/view/introduction" + "'>点我了解更多</a> \n");
        s.append("<a href='" + WeixinProperties.DOMAIN + "/page/view/vip" + "'>购买会员，享受更多权利</a> \n");

        return s.toString();
    }

    public static String hello1() {
        StringBuilder s = new StringBuilder();
        s.append("你好啊!\n\n");
        s.append("这是我的测试账号\n\n");
        s.append("输入的指令正确，指令为 1");

        return s.toString();
    }

    public static String defualt() {
        StringBuilder s = new StringBuilder();
        s.append("欢迎关注龙跃科技\n");
        s.append("如果想要做代理，请在个人中心页面查看客服联系方式\n");
        s.append("如果您是代理，请回复 代理 来获取自己的推广二维码\n");
        s.append("如果有人通过您的推广二维码成为会员，您将得到一定收益");
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

    public List<Item> news(String type,String offset,String count) {
//        JSONObject newsListObject = WechatUtil.getMaterialList("news", "0", "20");
        JSONObject newsListObject = WechatUtil.getMaterialList(type, offset, count);
        JSONArray itemArray = newsListObject.getJSONArray("item");

        JSONObject imagesListObject = WechatUtil.getMaterialList("image", "0", "20");
        JSONArray picItemArray = imagesListObject.getJSONArray("item");

        List<Item> items = new LinkedList<>();

        if (itemArray.size()==0)
            return null;

            JSONObject itemObject = (JSONObject) itemArray.get(itemArray.size()-1);
            JSONObject contentObject = itemObject.getJSONObject("content");
            JSONArray newsItemArray = contentObject.getJSONArray("news_item");
            for (int j = 0; j < newsItemArray.size(); j++) {
                JSONObject newsItemObject = (JSONObject) newsItemArray.get(j);

                Item item = new Item();
                item.setTitle(newsItemObject.getString("title"));
                item.setPicUrl(newsItemObject.getString("url"));
                item.setUrl(newsItemObject.getString("url"));
                item.setDescription("digest");

                String picId = newsItemObject.getString("thumb_media_id");

                for (int k = 0; k < picItemArray.size(); k++) {
                    JSONObject picObject = (JSONObject) picItemArray.get(k);
                    String media_id = picObject.getString("media_id");
                    try {
                        if (picId.equalsIgnoreCase(media_id)) {
                            item.setPicUrl(picObject.getString("url"));
                        }
                    } catch (Exception e) {
                        LoggerUtil.error(getClass(), "获取素材列表中的图片发生错误，可能是图文消息不存在", e);
                    }
                }


                items.add(item);
            }

        return items;
    }

    public List<Item> gaokao() {
        JSONObject newsListObject = WechatUtil.getMaterialList("news", "0", "20");
        JSONArray itemArray = newsListObject.getJSONArray("item");

        JSONObject imagesListObject = WechatUtil.getMaterialList("image", "0", "20");
        JSONArray picItemArray = imagesListObject.getJSONArray("item");

        List<Item> items = new LinkedList<>();

        if (itemArray.size()==0)
            return null;

        JSONObject itemObject = (JSONObject) itemArray.get(itemArray.size()-2);
        JSONObject contentObject = itemObject.getJSONObject("content");
        JSONArray newsItemArray = contentObject.getJSONArray("news_item");
        for (int j = 0; j < newsItemArray.size(); j++) {
            JSONObject newsItemObject = (JSONObject) newsItemArray.get(j);

            Item item = new Item();
            item.setTitle(newsItemObject.getString("title"));
            item.setPicUrl(newsItemObject.getString("url"));
            item.setUrl(newsItemObject.getString("url"));
            item.setDescription("digest");

            String picId = newsItemObject.getString("thumb_media_id");

            for (int k = 0; k < picItemArray.size(); k++) {
                JSONObject picObject = (JSONObject) picItemArray.get(k);
                String media_id = picObject.getString("media_id");
                try {
                    if (picId.equalsIgnoreCase(media_id)) {
                        item.setPicUrl(picObject.getString("url"));
                    }
                } catch (Exception e) {
                    LoggerUtil.error(getClass(), "获取素材列表中的图片发生错误，可能是图文消息不存在", e);
                }
            }


            items.add(item);
        }

        return items;
    }
}
