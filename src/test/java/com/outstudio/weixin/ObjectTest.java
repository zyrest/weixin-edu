package com.outstudio.weixin;

import com.outstudio.weixin.common.po.ManagerEntity;
import com.outstudio.weixin.common.po.UserEntity;
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
public class ObjectTest {

    @Test
    public void testConvert() {
        ManagerEntity manager = new ManagerEntity();
        manager.setId(111);
        manager.setM_account("samson");
        manager.setM_password("123456");

        Object o = convert(manager);
        System.out.println(o instanceof UserEntity);
//        UserEntity user = (UserEntity) o;
//        System.out.println(JSON.toJSONString(user));

    }

    private Object convert(Object o) {
        return o;
    }
}
