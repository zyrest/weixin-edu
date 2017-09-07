package com.outstudio.weixin.wechat.utils;

/**
 * Created by 96428 on 2017/7/14.
 */
public class ContentUtil {
    public static String hello1() {
        StringBuilder s = new StringBuilder();
        s.append("你好啊!\n\n");
        s.append("这是我的测试账号\n\n");
        s.append("输入的指令正确，指令为 1");

        return s.toString();
    }

    public static String onSubscribe(String nickname) {
        StringBuilder s = new StringBuilder();
        s.append(nickname);
        s.append("~");
        s.append("你好啊!\n\n");
        s.append("这是我的测试账号\n\n");
        s.append("欢迎您的关注\n\n");

        return s.toString();
    }
}
