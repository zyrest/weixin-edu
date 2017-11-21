package com.outstudio.weixin.wechat.core.handler;

import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.common.service.UserService;
import com.outstudio.weixin.common.utils.MessageVoUtil;
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

    @Resource
    private ContentUtil contentUtil;

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
        UserEntity user = WechatUtil.getUserInfoOnSubscribe(userOpenid);
        if (found == null) {
            userService.saveUser(user);
            found = user;
        }else {
            userService.updateUser(user);
        }

        String EventKey = messageMap.get("EventKey");
        if (EventKey != null) {
            int pid = Integer.parseInt(EventKey.substring(8));
            userService.setPid(pid, userOpenid);
        }

        return MessageUtil.createArticlesMessageXml(fromUser, userOpenid, contentUtil.news());
//        return MessageUtil.createTextMessageXml(fromUser, userOpenid, contentUtil.onSubscribe(found.getNickname()));
    }
}
