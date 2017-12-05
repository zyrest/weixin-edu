package com.outstudio.weixin.wechat.core.handler;

import com.alibaba.fastjson.JSONObject;
import com.outstudio.weixin.common.bitmatrix.utils.QRCodeUtil;
import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.common.service.UserService;
import com.outstudio.weixin.common.utils.LoggerUtil;
import com.outstudio.weixin.wechat.dto.message.media.Image;
import com.outstudio.weixin.wechat.dto.message.media.Item;
import com.outstudio.weixin.wechat.utils.ContentUtil;
import com.outstudio.weixin.wechat.utils.MessageUtil;
import com.outstudio.weixin.wechat.utils.WechatUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 96428 on 2017/9/7.
 * This in weixin-edu, com.outstudio.weixin.wechat.core.handler
 */
@Service("textMessageHandler")
public class TextMessageHandler implements Handler {

    @Resource
    private UserService userService;

    @Resource
    private ContentUtil contentUtil;

    @Override
    public String handler(Map<String, String> messageMap) {
        String content = messageMap.get("Content");
        String fromUser = messageMap.get("ToUserName");
        String toUser = messageMap.get("FromUserName");

        LoggerUtil.fmtDebug(getClass(), "用户输入内容是->{%s}", content);
        String result = "success";

        if (content.equals("代理")) {
            result = QRCodeResult(fromUser, toUser);
        } else if (content.equals("测试")) {
            result = test(fromUser, toUser);
        } else if (content.equals("高考")) {
            Integer newsCount = WechatUtil.getMaterialCount("news_count");
            String offset = String.valueOf(newsCount - 2);
            result = MessageUtil.createArticlesMessageXml(fromUser, toUser, contentUtil.news(offset, "1"));
        } else {
            result = MessageUtil.createTextMessageXml(fromUser, toUser, ContentUtil.defualt());
        }

        return result;
    }

    private JSONObject produceQRCode(Integer id) {
        return WechatUtil.produceQRCode("2592000", id.toString());
    }

    private String getQRCodeUrl(Integer id) {
        JSONObject object = produceQRCode(id);
        return object.getString("url");
    }

    /**
     * 将传入的content生成二维码,并将二维码以用户id命名存在本地
     *
     * @param content  二维码的内容
     * @param fileName 保存在本地的文件名
     */
    private void saveToLocal(String content, String fileName) {
        try {
//                QRCodeUtil.generateQRCode(WeixinProperties.DOMAIN + "/page/view/vip#" + id, 400, 400, "png", "/home/ubuntu/weixin/qrcode/" + id + ".png");
            QRCodeUtil.generateQRCode(content, 400, 400, "png", "/home/ubuntu/weixin/qrcode/" + fileName + ".png");
            LoggerUtil.debug(getClass(), "生成二维码成功");
        } catch (Exception e) {
            e.printStackTrace();
            LoggerUtil.debug(getClass(), "生成二维码失败");
        }

    }


    private String QRCodeResult(String fromUser, String toUser) {

        //获取用户的id
        UserEntity userEntity = userService.getUserByOpenId(toUser);

        if (userEntity.getLevel() == 0) {
            return MessageUtil.createTextMessageXml(fromUser, toUser, "对不起，您还未成为代理，有意者请联系-尹 15077107934  0771-2717765");
        }
        Integer id = userEntity.getId();

        String qrcodeUrl = getQRCodeUrl(id);

        //生成二维码并保存在本地
        saveToLocal(qrcodeUrl, id.toString());

        //将二维码上传到微信服务器并拿到media_id
        String media_id = WechatUtil.uploadLocalToWeixin("/home/ubuntu/weixin/qrcode/" + id + ".png");

        Image image = new Image();
        if (media_id != null) {
            image.setMediaId(media_id);
            return MessageUtil.createImageMessageXml(fromUser, toUser, image);
        }
        return "success";
    }

    private String test(String fromUser, String toUser) {
        List<Item> items = new ArrayList<>();

        Item one = new Item();
        one.setTitle("测试内容1");
        one.setDescription("csdn博客");
        one.setUrl("http://blog.csdn.net/");
        one.setPicUrl("http://ss.csdn.net/p?http://mmbiz.qpic.cn/mmbiz_jpg/1hReHaqafaflH9mgsL0dialsMGWcxxEXORvRwqKGxCxgxXq5OickOWdRB3DJQNjTnFQ7O2iasQFQMhaaIMGecU1Zw/640?wx_fmt=jpeg");

        Item two = new Item();
        two.setTitle("测试内容2");
        two.setDescription("还是csdn博客");
        two.setUrl("http://blog.csdn.net/");
        two.setPicUrl("http://ss.csdn.net/p?http://mmbiz.qpic.cn/mmbiz_jpg/1hReHaqafaflH9mgsL0dialsMGWcxxEXORvRwqKGxCxgxXq5OickOWdRB3DJQNjTnFQ7O2iasQFQMhaaIMGecU1Zw/640?wx_fmt=jpeg");

        items.add(one);
        items.add(two);

        return MessageUtil.createArticlesMessageXml(fromUser, toUser, items);
    }
}
