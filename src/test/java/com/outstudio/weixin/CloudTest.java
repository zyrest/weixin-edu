package com.outstudio.weixin;

import com.outstudio.weixin.cloud.util.CloudUtil;
import org.junit.Test;

public class CloudTest {

    @Test
    public void deleteVodFileTest() {
        CloudUtil.deleteVodFile("9031868223647204521");
    }
}
