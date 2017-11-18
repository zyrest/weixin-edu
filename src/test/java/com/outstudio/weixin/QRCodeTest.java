package com.outstudio.weixin;

import com.google.zxing.qrcode.encoder.QRCode;
import com.outstudio.weixin.common.bitmatrix.utils.QRCodeUtil;
import com.outstudio.weixin.common.utils.DateUtil;
import org.junit.Test;

import java.util.Date;

/**
 * Created by lmy on 2017/11/17.
 */
public class QRCodeTest {

    @Test
    public void generate() {
        try {
            QRCodeUtil.generateQRCode("http://www.baidu.com", 400, 400, "png","E:\\qrcode.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parse() {
        Date now = DateUtil.dateAdd(new Date(), 30);
        System.out.println(now.toString());
    }
}
