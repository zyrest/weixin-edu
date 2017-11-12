package com.outstudio.weixin;

import com.outstudio.weixin.common.dao.UserEntityMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUser {

    @Resource
    UserEntityMapper userEntityMapper;

    @Test
    public void test() {
        System.out.println(userEntityMapper.getAllUsers().isEmpty());
    }
}
