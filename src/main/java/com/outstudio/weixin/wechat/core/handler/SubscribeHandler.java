package com.outstudio.weixin.wechat.core.handler;

import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.common.service.UserService;
import com.outstudio.weixin.wechat.utils.ContentUtil;
import com.outstudio.weixin.wechat.utils.MessageUtil;
import com.outstudio.weixin.wechat.utils.WechatUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by 96428 on 2017/9/7.
 * This in weixin-edu, com.outstudio.weixin.wechat.core.handler
 */
@Service("subscribeHandler")
public class SubscribeHandler implements Handler {

    @Resource
    private UserService userService;

    /**
     * 当用户关注公众号时, 自动拉取用户信息, 保存在用户数据库中
     * @param messageMap 获得到的信息
     * @return 问候消息
     */
    @Override
    public String handler(Map<String, String> messageMap) {
        String fromUser = messageMap.get("ToUserName");
        String userOpenid = messageMap.get("FromUserName");

        UserEntity found = userService.getUserByOpenId(userOpenid);
        if (found == null) {
            UserEntity user = WechatUtil.getUserInfoOnSubscribe(userOpenid);
            userService.saveUser(user);
            found = user;
        }

        return MessageUtil.createTextMessageXml(fromUser, userOpenid, ContentUtil.onSubscribe(found.getNickname()));
    }
}
