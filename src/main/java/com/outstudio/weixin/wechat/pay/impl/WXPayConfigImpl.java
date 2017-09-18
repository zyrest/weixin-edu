package com.outstudio.weixin.wechat.pay.impl;

import com.outstudio.weixin.wechat.config.WeixinProperties;
import com.outstudio.weixin.wechat.pay.sdk.IWXPayDomain;
import com.outstudio.weixin.wechat.pay.sdk.WXPayConfig;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class WXPayConfigImpl extends WXPayConfig{

    /**
     本地测试，先不使用证书，有证书的情况要去掉注释并且设置好证书文件的位置
     */
    private byte[] certData;
    private static WXPayConfigImpl INSTANCE;

    private WXPayConfigImpl() throws Exception{
        /**
         本地测试，先不使用证书，有证书的情况要去掉注释并且设置好证书文件的位置
         */
//        String certPath = "D://CERT/common/apiclient_cert.p12";
//        File file = new File(certPath);
//        InputStream certStream = new FileInputStream(file);
//        this.certData = new byte[(int) file.length()];
//        certStream.read(this.certData);
//        certStream.close();
    }

    public static WXPayConfigImpl getInstance() throws Exception{
        if (INSTANCE == null) {
            synchronized (WXPayConfigImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WXPayConfigImpl();
                }
            }
        }
        return INSTANCE;
    }

    public String getAppID() {
        return WeixinProperties.appID;
    }

    public String getMchID() {
        return WeixinProperties.mchID;
    }

    public String getKey() {
        return WeixinProperties.appKey;
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis;
        certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }


    public int getHttpConnectTimeoutMs() {
        return 2000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    public IWXPayDomain getWXPayDomain() {
        return WXPayDomainSimpleImpl.instance();
    }

    public String getPrimaryDomain() {
        return "api.mch.weixin.qq.com";
    }

    public String getAlternateDomain() {
        return "api2.mch.weixin.qq.com";
    }

    @Override
    public int getReportWorkerNum() {
        return 1;
    }

    @Override
    public int getReportBatchSize() {
        return 2;
    }
}
