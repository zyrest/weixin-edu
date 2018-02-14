package com.outstudio.weixin.wechat.utils;


import com.outstudio.weixin.wechat.config.EventType;
import com.outstudio.weixin.wechat.config.WeixinProperties;
import com.outstudio.weixin.wechat.dto.menu.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 96428 on 2017/7/16.
 */
public class MenuUtil {

    private static final String domain = WeixinProperties.DOMAIN;

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
        Button button = new Button();
        button.setName("带动熏陶");

        List<Button> sub_buttons = new ArrayList<>();

        sub_buttons.add(createViewButton("经典儿歌", "http://mp.weixin.qq.com/s/xv4tQenLcgafwbLfNlZvAQ"));
        sub_buttons.add(createViewButton("故事培养", domain + "/page/view/audio"));
        sub_buttons.add(createViewButton("感动心灵", domain + "/page/view/gandong"));
        sub_buttons.add(createViewButton("童星展示", domain + "todo"));//todo  童星展示

        button.setSub_button(sub_buttons);
        return button;
    }

    private static Button secondButton() {
        Button button = new Button();
        button.setName("文化教育");

        List<Button> sub_buttons = new ArrayList<>();

        sub_buttons.add(createViewButton("英语课堂", domain+"/page/view/english"));
        sub_buttons.add(createViewButton("数学课堂", domain+"/page/view/math"));
        sub_buttons.add(createViewButton("物理课堂", domain+"/page/view/physics"));
        sub_buttons.add(createViewButton("化学课堂", domain+"/page/view/chemistry"));
        sub_buttons.add(createViewButton("教育讨论", domain+"/page/view/video"));
//        sub_buttons.add(createViewButton("复习内容", domain + "/page/view/english#review"));

        button.setSub_button(sub_buttons);
        return button;
    }

    private static Button thirdButton() {
        Button button = new Button();
        button.setName("家长关注");

        List<Button> sub_button = new ArrayList<>();

        sub_button.add(createViewButton("个人中心", domain+"/page/view/userInfo"));
        sub_button.add(createViewButton("VIP注册", domain+"/page/view/vip"));
        sub_button.add(createViewButton("投稿",domain+"/page/view/voiceInHome")); //todo 投稿
        sub_button.add(createViewButton("平台介绍", domain + "/page/view/introduction"));

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
