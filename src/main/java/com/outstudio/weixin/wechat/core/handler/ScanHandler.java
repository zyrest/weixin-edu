package com.outstudio.weixin.wechat.core.handler;

import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.common.service.UserService;
import com.outstudio.weixin.wechat.utils.ContentUtil;
import com.outstudio.weixin.wechat.utils.MessageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by lmy on 2017/11/20.
 */
@Service("scanHandler")
public class ScanHandler implements Handler {

    @Resource
    private UserService userService;

    @Resource
    private ContentUtil contentUtil;

    @Override
    public String handler(Map<String, String> messageMap) {
        String fromUser = messageMap.get("ToUserName");
        String userOpenid = messageMap.get("FromUserName");
        String EventKey = messageMap.get("EventKey");
        int pid = Integer.parseInt(EventKey);

        UserEntity user = userService.getUserByOpenId(userOpenid);
        if (user.getPid() == 0) {
            userService.setPid(pid, userOpenid);
            return MessageUtil.createTextMessageXml(fromUser, userOpenid, contentUtil.onScan());
        } else
            return MessageUtil.createTextMessageXml(fromUser, userOpenid, contentUtil.onHasPidScan());
    }
}
