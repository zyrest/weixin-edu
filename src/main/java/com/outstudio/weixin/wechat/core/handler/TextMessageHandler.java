package com.outstudio.weixin.wechat.core.handler;

import com.alibaba.fastjson.JSONObject;
import com.outstudio.weixin.common.bitmatrix.utils.QRCodeUtil;
import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.common.service.UserService;
import com.outstudio.weixin.common.utils.LoggerUtil;
import com.outstudio.weixin.wechat.cache.AccessTokenCache;
import com.outstudio.weixin.wechat.config.WeixinProperties;
import com.outstudio.weixin.wechat.dto.message.media.Image;
import com.outstudio.weixin.wechat.dto.message.media.Item;
import com.outstudio.weixin.wechat.utils.ContentUtil;
import com.outstudio.weixin.wechat.utils.MessageUtil;
import com.outstudio.weixin.wechat.utils.WechatUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
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

    @Override
    public String handler(Map<String, String> messageMap) {
        String content = messageMap.get("Content");
        String fromUser = messageMap.get("ToUserName");
        String toUser = messageMap.get("FromUserName");

        LoggerUtil.fmtDebug(getClass(), "用户输入内容是->{%s}", content);
        String result = "success";

        if (content.equals("代理")) {

            //获取用户的id
            UserEntity userEntity = userService.getUserByOpenId(toUser);
            Integer id = userEntity.getId();

            JSONObject object = createQRCode(id);
            String qrcodeUrl = object.getString("url");

            //生成二维码并保存在本地
            try {
//                QRCodeUtil.generateQRCode(WeixinProperties.DOMAIN + "/page/view/vip#" + id, 400, 400, "png", "/home/ubuntu/weixin/qrcode/" + id + ".png");
                QRCodeUtil.generateQRCode(qrcodeUrl, 400, 400, "png", "/home/ubuntu/weixin/qrcode/" + id + ".png");
                LoggerUtil.debug(getClass(), "生成二维码成功");
            } catch (Exception e) {
                e.printStackTrace();
                LoggerUtil.debug(getClass(), "生成二维码失败");
            }


            //将二维码上传到微信服务器并拿到media_id
            String url = String.format(WeixinProperties.UPLOAD_TEMPORARY_MATERIAL,
                    AccessTokenCache.getAccessToken().getAccess_token(),
                    "image");
            File file = new File("/home/ubuntu/weixin/qrcode/" + id + ".png");

            JSONObject jsonObject = WechatUtil.httpRequest(url, file);
            Image image = new Image();
            String media_id = jsonObject.getString("media_id");
            if (media_id != null) {
                image.setMediaId(media_id);
                result = MessageUtil.createImageMessageXml(fromUser, toUser, image);
            }
        } else if (content.equals("测试")) {
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

            result = MessageUtil.createArticlesMessageXml(fromUser, toUser, items);
        } else {
            result = MessageUtil.createTextMessageXml(fromUser, toUser, ContentUtil.defualt());
        }

        return result;
    }

    private JSONObject createQRCode(Integer id) {
        return WechatUtil.produceQRCode("2592000", id.toString());
    }
}
