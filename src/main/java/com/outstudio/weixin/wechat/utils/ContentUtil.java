package com.outstudio.weixin.wechat.utils;

import com.outstudio.weixin.common.service.WelcomeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by 96428 on 2017/7/14.
 */
@Service
public class ContentUtil {

    @Resource
    private WelcomeService welcomeService;

    public static String hello1() {
        StringBuilder s = new StringBuilder();
        s.append("你好啊!\n\n");
        s.append("这是我的测试账号\n\n");
        s.append("输入的指令正确，指令为 1");

        return s.toString();
    }

    public String onSubscribe(String nickname) {

        StringBuilder s = new StringBuilder();
        s.append(nickname);
        s.append("~");
        s.append("你好啊!\n\n");
        s.append("欢迎您的关注\n\n");
        return s.append(welcomeService.getByIsUsing(1).getContent().replace("\\n", "\n")).toString();
    }
}
