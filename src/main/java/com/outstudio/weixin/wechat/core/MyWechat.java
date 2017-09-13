package com.outstudio.weixin.wechat.core;

import com.alibaba.fastjson.JSON;
import com.outstudio.weixin.common.utils.LoggerUtil;
import com.outstudio.weixin.wechat.core.router.Router;
import com.outstudio.weixin.wechat.utils.MessageUtil;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * Created by 96428 on 2017/7/15.
 */
@Service
public class MyWechat {
    private HttpServletRequest request;
    private Map<String, String> messageMap;

    @Resource(name = "normalRouter")
    private Router normalRouter;

    public MyWechat() {}

    public void setRequest(HttpServletRequest request) {
        LoggerUtil.fmtDebug(getClass(), "开始处理消息请求");
        this.request = request;
        init();
    }
    private void init() {
        try {
            messageMap = MessageUtil.xml2Map(request);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }

    public String execute() {
        LoggerUtil.fmtDebug(getClass(), "正在获取消息回复");

        System.out.println(JSON.toJSONString(messageMap));
        String result = normalRouter.dispatchMessage(messageMap);
        if (result == null) {
            result = "success";
        }

        LoggerUtil.fmtDebug(getClass(), "回复为 -> \n%s", result);
        return result;
    }
}
