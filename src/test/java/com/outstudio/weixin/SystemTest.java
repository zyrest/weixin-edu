package com.outstudio.weixin;

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
        System.out.println("weixin_edu".getBytes());
        System.out.println(Base64.getEncoder().encode("weixin_edu".getBytes()));
    }
}
