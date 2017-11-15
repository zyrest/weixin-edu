package com.outstudio.weixin;

import com.alibaba.fastjson.JSON;
import com.outstudio.weixin.wechat.config.WeixinProperties;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lmy on 2017/9/18.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ConfigTest {

    private String fileSavedPath;

    private PasswordService passwordService = new DefaultPasswordService();

    public String getFileSavedPath() {
        return fileSavedPath;
    }

    @Value("${fileSavedPath}")
    public void setFileSavedPath(String fileSavedPath) {
        this.fileSavedPath = fileSavedPath;
    }

    @Test
    public void test() {
        String password = passwordService.encryptPassword("asd");
//        System.out.println(password);
//
//        System.out.println(passwordService.passwordsMatch("asd", password));
        String url = "http://localhost:8080/mp3/742463b4-523c-40ac-ad30-4286e09763d7林宥嘉 - 你是我的眼.mp3";
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        String newUrl = url.substring(0, url.lastIndexOf("/")) + "/free/" + fileName;
        System.out.println(url);

        System.out.println(newUrl);

    }

    @Test
    public void test2() {
//        System.out.println(String.format(WeixinProperties.GET_MATERIAL_LIST_URL,"sssssssss"));
        Map<String, String> params = new HashMap<>();
        params.put("type", "1312");
        params.put("offset", "213");
        params.put("count", "sad");
        String result = JSON.toJSONString(params);
        System.out.println(result);
    }
}
