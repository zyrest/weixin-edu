package com.outstudio.weixin.wechat.core.handler;

import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Created by 96428 on 2017/7/15.
 */
public interface Handler {
    Logger logger = Logger.getLogger(Handler.class);

    String dispatchMessage(Map<String, String> messageMap);
}
