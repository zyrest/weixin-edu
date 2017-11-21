package com.outstudio.weixin.page.controller;

import com.alibaba.fastjson.JSON;
import com.outstudio.weixin.common.service.ChargeService;
import com.outstudio.weixin.common.utils.DateUtil;
import com.outstudio.weixin.common.utils.LoggerUtil;
import com.outstudio.weixin.core.shiro.token.TokenManager;
import com.outstudio.weixin.wechat.pay.impl.WXPayConfigImpl;
import com.outstudio.weixin.wechat.pay.sdk.WXPay;
import com.outstudio.weixin.wechat.pay.sdk.WXPayConstants;
import com.outstudio.weixin.wechat.pay.sdk.WXPayUtil;
import com.outstudio.weixin.wechat.utils.MessageUtil;
import org.dom4j.DocumentException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lmy on 2017/9/16.
 */
@RestController
@RequestMapping("/open/page")
public class PayController {

    private WXPay wxPay;
    private WXPayConfigImpl config;

    private static final String backOffUrl = "http://www.here52.cn/open/page/wxpayDone";

    @Resource
    private ChargeService chargeService;

    private void init() {
        try {
            config = WXPayConfigImpl.getInstance();
            wxPay = new WXPay(config, backOffUrl);

        } catch (Exception e) {
            LoggerUtil.error(getClass(), e.getMessage());
            e.printStackTrace();

        }
    }

    @RequestMapping("/wxpay")
    public Map<String, String> pay(HttpServletRequest request,
                                   @RequestParam("fee") String fee) {

        init();
        Map<String, String> returnMap = new HashMap<>();

        HashMap<String, String> data = new HashMap<>();
        data.put("body", "vip充值");
        data.put("out_trade_no", DateUtil.getFormatDate());
        data.put("device_info", "WEB");
        data.put("total_fee", fee);
        data.put("spbill_create_ip", request.getRemoteAddr());
        data.put("trade_type", "JSAPI");
        data.put("openid", TokenManager.getWeixinToken().getOpenid());

        LoggerUtil.fmtDebug(getClass(), "支付信息", data.toString());

        Map<String, String> result = null;
        try {
            result = wxPay.unifiedOrder(data);
            processPayResult(result);
        } catch (Exception e) {
            returnMap.put("status", "400");
            LoggerUtil.error(getClass(), e.getMessage());
            LoggerUtil.error(getClass(), JSON.toJSONString(result));
            return returnMap;
        }

        //生成H5调起微信支付API相关参数（前端页面js的配置参数）
        long timestamp = DateUtil.getTimestampSeconds();//当前时间的时间戳（秒）
        String packages = "prepay_id=" + result.get("prepay_id");//订单详情扩展字符串
        String nonceStr = WXPayUtil.generateUUID();

        returnMap.put("appId", config.getAppID());//公众号appid
        returnMap.put("timeStamp", String.valueOf(timestamp));
        returnMap.put("nonceStr", nonceStr); //随机数
        returnMap.put("package", packages);
        returnMap.put("signType", WXPayConstants.HMACSHA256);//签名方式
        String sign = null;
        try {
            sign = WXPayUtil.generateSignature(returnMap, config.getKey(), WXPayConstants.SignType.HMACSHA256);
        } catch (Exception e) {
            LoggerUtil.fmtDebug(getClass(), "生成Sign出错，message：-> {%s}", e.getMessage());
            returnMap.put("status", "401");
            return returnMap;
        }
        returnMap.put("paySign", sign);
        returnMap.put("status", "200");

        LoggerUtil.fmtDebug(getClass(), JSON.toJSONString(returnMap));
        return returnMap;
    }

    private void processPayResult(Map<String, String> result) throws Exception {
        if ("SUCCESS".equalsIgnoreCase(result.get("return_code"))) {

            if ("SUCCESS".equalsIgnoreCase(result.get("result_code"))) {
                chargeService.preCharge(TokenManager.getWeixinToken().getOpenid());
            } else {
                throw new Exception("err_code：" + result.get("err_code") + "，err_code_des：" + result.get("err_code_des"));
            }

        } else {
            throw new Exception("return_msg：" + result.get("return_msg"));
        }
    }

    @RequestMapping("/wxpayDone")
    public String processResult(HttpServletRequest request) throws IOException, DocumentException {

        Map<String, String> map = MessageUtil.xml2Map(request);

        LoggerUtil.fmtDebug(getClass(), "response message is : -> {%s}", JSON.toJSONString(map));
        String return_code = map.get("return_code");
        String return_msg = map.get("return_msg");

        String stringResult = null;
        if ("SUCCESS".equalsIgnoreCase(return_code)) {
            String result_code = map.get("result_code");
            if ("SUCCESS".equalsIgnoreCase(result_code)) {
                // 成功将成功信息写进数据库，返回SUCCESS
                String openid = map.get("openid");
                String transaction_id = map.get("transaction_id");
                String out_trade_no = map.get("out_trade_no");
                String now_date = map.get("time_end");
                String total_fee = map.get("total_fee");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = null;
                try {
                    date = sdf.parse(now_date);
                    LoggerUtil.fmtDebug(getClass(),"格式化后的交易日期为%s",date.toString());
                } catch (ParseException e) {
                    LoggerUtil.error(getClass(), "日期格式转化错误", e);
                }

                chargeService.charge(openid, out_trade_no,transaction_id, date, total_fee);

                Map<String, String> result = new HashMap<>();
                result.put("return_code", "SUCCESS");
                result.put("return_msg", "OK");
                try {
                    stringResult = WXPayUtil.mapToXml(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return stringResult;

            } else {
                // 失败，返回FAIL
                String err_code = map.get("err_code");
                String err_code_des = map.get("err-code_des");
                Map<String, String> result = new HashMap<>();
                result.put("return_code", "FAIL");
                result.put("return_msg", err_code + "----" + err_code_des);
                try {
                    stringResult = WXPayUtil.mapToXml(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return stringResult;
            }
        }

        Map<String, String> result = new HashMap<>();
        result.put("return_code", "FAIL");
        result.put("return-msg", return_msg);
        try {
            stringResult = WXPayUtil.mapToXml(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringResult;
    }
}
