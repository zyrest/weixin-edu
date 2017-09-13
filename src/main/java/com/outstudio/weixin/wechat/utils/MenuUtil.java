package com.outstudio.weixin.wechat.utils;


import com.outstudio.weixin.wechat.config.EventType;
import com.outstudio.weixin.wechat.config.WeixinProperties;
import com.outstudio.weixin.wechat.dto.menu.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 96428 on 2017/7/16.
 */
public class MenuUtil {
    /**
     * 创建一个默认的菜单栏
     *
     * @return 默认菜单栏
     */
    public static Menu initMenus() {
        Menu menu = new Menu();
        List<Button> buttons = Arrays.asList(firstButton(), secondButton(), thirdButton());
        menu.setButton(buttons);
        return menu;
    }

    private static Button firstButton() {
        return createViewButton("培训故事", String.format(WeixinProperties.USER_INFO_CODE, WeixinProperties.appID, WeixinProperties.OAUTH_REDIRECT_URL, WeixinProperties.USERINFO_SCOPE, 1));
    }

    private static Button secondButton() {
        Button button = new Button();
        button.setName("启蒙英语");

        List<Button> sub_buttons = new ArrayList<>();
        sub_buttons.add(createViewButton("英语课堂", "http://www.baidu.com/"));
        sub_buttons.add(createViewButton("复习内容", "https://www.qunar.com/"));
        button.setSub_button(sub_buttons);

        return button;
    }

    private static Button thirdButton() {
        Button button = new Button();
        List<Button> sub_button = new ArrayList<>();
        sub_button.add(createViewButton("VIP注册", "http://www.baidu.com/"));
        sub_button.add(createViewButton("访谈讲堂", "http://www.baidu.com/"));
        sub_button.add(createViewButton("积分兑换", "http://www.baidu.com/"));
        sub_button.add(createViewButton("通知", "http://www.baidu.com/"));
        button.setName("家长关注");
        button.setSub_button(sub_button);
        return button;
    }

    private static ViewButton createViewButton(String name, String url) {
        ViewButton button = new ViewButton();
        button.setType(EventType.EVENT_view);
        button.setName(name);
        button.setUrl(url);

        return button;
    }

    private static ClickButton createClickButton(String name, String key) {
        ClickButton button = new ClickButton();
        button.setType(EventType.EVENT_click);
        button.setName(name);
        button.setKey(key);

        return button;
    }

    private static MiniProgramButton createMiniProgramButton(String name, String url,
                                                             String appid, String pagepath) {
        MiniProgramButton button = new MiniProgramButton();
        button.setType(EventType.EVENT_MINIPROGRAM);
        button.setName(name);
        button.setUrl(url);
        button.setAppid(appid);
        button.setPagepath(pagepath);

        return button;
    }
}
