package com.outstudio.weixin.wechat.core.router;

import java.util.Map;

/**
 * Created by 96428 on 2017/7/15.
 */
public interface Router {

    String dispatchMessage(Map<String, String> messageMap);
}
