package com.outstudio.weixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.outstudio.weixin.common.utils.NetWorkUtil;
import com.outstudio.weixin.wechat.config.WeixinProperties;
import org.junit.Test;

import java.util.Arrays;
import java.util.Base64;

/**
 * Created by lmy on 2017/11/17.
 */
public class SystemTest {

    @Test
    public void test() {
        System.out.println(System.getProperty("os.name"));
    }

    @Test
    public void test2() {
//        System.out.println("weixin_edu".getBytes());
//        System.out.println(Base64.getEncoder().encode("weixin_edu".getBytes()));
//        System.out.println("qrscene_123123".substring(8));
        System.out.println(JSON.toJSONString("{expire_seconds: 604800, action_name: QR_STR_SCENE, action_info: {scene: {scene_str: test}}}"));
    }
}
