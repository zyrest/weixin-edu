package com.outstudio.weixin.wechat.utils;


import com.outstudio.weixin.wechat.config.EventType;
import com.outstudio.weixin.wechat.dto.menu.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 96428 on 2017/7/16.
 */
public class MenuUtil {
    /**
     * 创建一个默认的菜单栏
     * @return 默认菜单栏
     */
    public static Menu initMenus() {
        Menu menu = new Menu();
        List<Button> buttons = Arrays.asList(firstButton(), secondButton(), thirdButton());
        menu.setButton(buttons);
        return menu;
    }

    private static Button firstButton() {
//        List<Button> sub_buttons = new ArrayList<>();
//        sub_buttons.add(createViewButton());

        return createViewButton("问一下", "http://www.baidu.com/");
    }

    private static Button secondButton() {
        Button button = new Button();
        button.setName("上拉菜单");

        List<Button> sub_buttons = new ArrayList<>();
        sub_buttons.add(createClickButton("发送1", "1"));
        sub_buttons.add(createViewButton("去哪儿网", "https://www.qunar.com/"));
        button.setSub_button(sub_buttons);

        return button;
    }

    private static Button thirdButton() {
        return createViewButton("约约约", "http://www.qq.com/");
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
