package com.outstudio.weixin.wechat.core.handler;

import java.util.Map;

/**
 * Created by 96428 on 2017/9/7.
 * This in weixin-edu, com.outstudio.weixin.wechat.core.handler
 */
public interface Handler {
    String handler(Map<String, String> messageMap);
}
