package com.outstudio.weixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.common.utils.DateUtil;
import com.outstudio.weixin.common.utils.PasswordUtil;
import com.outstudio.weixin.wechat.utils.MessageUtil;
import org.dom4j.DocumentException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("body", "vip充值");
        data.put("out_trade_no", DateUtil.getFormatDate());
        data.put("device_info", "WEB");
        data.put("total_fee", "1");
        data.put("spbill_create_ip", "192.168.0.1");
        data.put("notify_url", "http://localhost:8080/test");
        data.put("trade_type", "JSAPI");
        System.out.println(JSON.toJSON(data));
    }

    @Test
    public void xml() throws DocumentException {
        String xml = "<xml>\n" +
                "  <appid><![CDATA[wx2421b1c4370ec43b]]></appid>\n" +
                "  <attach><![CDATA[支付测试]]></attach>\n" +
                "  <bank_type><![CDATA[CFT]]></bank_type>\n" +
                "  <fee_type><![CDATA[CNY]]></fee_type>\n" +
                "  <is_subscribe><![CDATA[Y]]></is_subscribe>\n" +
                "  <mch_id><![CDATA[10000100]]></mch_id>\n" +
                "  <nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str>\n" +
                "  <openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid>\n" +
                "  <out_trade_no><![CDATA[1409811653]]></out_trade_no>\n" +
                "  <result_code><![CDATA[SUCCESS]]></result_code>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <sign><![CDATA[B552ED6B279343CB493C5DD0D78AB241]]></sign>\n" +
                "  <sub_mch_id><![CDATA[10000100]]></sub_mch_id>\n" +
                "  <time_end><![CDATA[20140903131540]]></time_end>\n" +
                "  <total_fee>1</total_fee>\n" +
                "  <trade_type><![CDATA[JSAPI]]></trade_type>\n" +
                "  <transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id>\n" +
                "</xml>";

        Map<String, String> map = MessageUtil.xml2Map(xml);
        System.out.println(new Date(1513594023000L));
    }
}
