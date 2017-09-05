package com.outstudio.weixin.wechat.core;

import com.outstudio.weixin.wechat.core.handler.Handler;
import com.outstudio.weixin.wechat.core.handler.NormalHandler;
import com.outstudio.weixin.wechat.utils.MessageUtil;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * Created by 96428 on 2017/7/15.
 */
@Service
public class MyWechat {
    private Logger logger = Logger.getLogger(MyWechat.class);
    private HttpServletRequest request;
    private Map<String, String> messageMap;

    private Handler handler = new NormalHandler();

//    public MyWechat(HttpServletRequest request) {
//        logger.info("start handle wenxin request");
//        this.request = request;
//        init();
//    }

    public MyWechat() {}

    void setRequest(HttpServletRequest request) {
        logger.info("start handle wenxin request");
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
        logger.info("getting response message");
//        logger.info(handler == null);
        String result = handler.dispatchMessage(messageMap);
        if (result == null) {
            result = "success";
        }
        logger.info("got response message : \n" + result);
        return result;
    }







}
