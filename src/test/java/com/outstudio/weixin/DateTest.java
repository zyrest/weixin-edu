package com.outstudio.weixin;

import com.outstudio.weixin.common.utils.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Time;
import java.util.Date;

/**
 * Created by lmy on 2017/9/12.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DateTest {
    @Test
    public void test() {
//        Date oldDate = DateUtil.dateAdd(new Date(), 365);
//        System.out.println(new Timestamp(DateUtil.dateAdd(new Date(),-365).getTime()));
//        System.out.println(DateUtil.isNotExpire(oldDate));

        System.out.println(String.valueOf(new Time(new Date().getTime())));
        System.out.println(new Date().getYear()+1900);
        System.out.println(new Date().getMonth()+1);
        System.out.println(new Date().getDate());

        System.out.println(DateUtil.getFormatDate());// new Date()为获取当前系统时间

    }
}
