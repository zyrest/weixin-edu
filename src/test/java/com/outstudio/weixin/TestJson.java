package com.outstudio.weixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.common.utils.FileUtil;
import com.outstudio.weixin.common.utils.PasswordUtil;
import com.outstudio.weixin.wechat.utils.MenuUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by 96428 on 2017/9/7.
 * This in weixin-edu, com.outstudio.weixin
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestJson {
    @Test
    public void testJson() {
        JSONObject json = new JSONObject();
        json.put("openid", "111");
        json.put("youdonthave", "555");
        UserEntity user = JSON.parseObject(json.toJSONString(), UserEntity.class);

        System.out.println(JSON.toJSONString(user));
    }
    @Test
    public void testPass() {
        String pass = "123456";
        String encoded = PasswordUtil.createHash(pass);
        System.out.println(encoded);
    }

    @Test
    public void test() {

        System.out.println(JSON.toJSONString(MenuUtil.initMenus()));
    }
}
