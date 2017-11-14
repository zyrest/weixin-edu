package com.outstudio.weixin;

import com.outstudio.weixin.cloud.util.CloudUtil;
import com.outstudio.weixin.common.dao.UserEntityMapper;
import com.outstudio.weixin.common.utils.PasswordUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.TreeMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUser {

    @Resource
    UserEntityMapper userEntityMapper;

    @Test
    public void test() {
        System.out.println(userEntityMapper.getUserById(1));
    }

    @Test
    public void t() {
        System.out.println(PasswordUtil.createHash("qwerty"));
    }

    @Test
    public void vodApiTest() {
//        VodApi vodApi = new VodApi(VodProperties.secretId, VodProperties.secretKey);
//        System.out.println(JSON.toJSONString(MenuUtil.initMenus()));

//        System.out.println(new Date().getTime());
//        System.out.println(new SecureRandom().nextInt());
//        System.out.println(new Random().nextInt());

        CloudUtil.deleteVodFile("asd",1,1);
    }


}
