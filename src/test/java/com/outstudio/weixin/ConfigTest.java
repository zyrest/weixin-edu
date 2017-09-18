package com.outstudio.weixin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by lmy on 2017/9/18.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ConfigTest {

    private String fileSavedPath;

    public String getFileSavedPath() {
        return fileSavedPath;
    }

    @Value("${fileSavedPath}")
    public void setFileSavedPath(String fileSavedPath) {
        this.fileSavedPath = fileSavedPath;
    }

    @Test
    public void test() {
        System.out.println(getFileSavedPath());

    }
}
