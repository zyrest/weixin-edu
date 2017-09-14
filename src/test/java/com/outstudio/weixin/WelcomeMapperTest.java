package com.outstudio.weixin;

import com.alibaba.fastjson.JSONObject;
import com.outstudio.weixin.common.dao.WelcomeEntityMapper;
import com.outstudio.weixin.common.po.WelcomeEntity;
import com.outstudio.weixin.common.service.WelcomeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Created by lmy on 2017/9/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WelcomeMapperTest {

    @Resource
    WelcomeService service;

    @Test
    public void testGet() {
        System.out.println(JSONObject.toJSONString(service.getAll()));
        System.out.println(JSONObject.toJSONString(service.getById(1)));

    }

    @Test
    public void testPost() {
        WelcomeEntity welcomeEntity = new WelcomeEntity();
        welcomeEntity.setContent("daaaasdsad");
        service.post(welcomeEntity);
    }

    @Test
    public void testDelete() {
        System.out.println(JSONObject.toJSONString(service.deleteById(3)));

    }

    @Test
    public void testUpdate() {
        System.out.println(JSONObject.toJSONString(service.useById(2)));

    }

}
