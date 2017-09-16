package com.outstudio.weixin.page.controller;

import com.alibaba.fastjson.JSONArray;
import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.common.service.ChargeService;
import com.outstudio.weixin.common.utils.DateUtil;
import com.outstudio.weixin.common.utils.IpUtil;
import com.outstudio.weixin.common.utils.LoggerUtil;
import com.outstudio.weixin.core.shiro.token.TokenManager;
import com.outstudio.weixin.wechat.pay.impl.WXPayConfigImpl;
import com.outstudio.weixin.wechat.pay.sdk.WXPay;
import com.outstudio.weixin.wechat.pay.sdk.WXPayUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @Resource
    private ChargeService chargeService;

    private void init() {
        try {
            config = WXPayConfigImpl.getInstance();
            wxPay = new WXPay(config);

        } catch (Exception e) {
            LoggerUtil.error(getClass(), e.getMessage());
            e.printStackTrace();

        }
    }

    @RequestMapping("/wxpay")
    public Object pay(HttpServletRequest request) {

        init();

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("body", "vip充值");
        data.put("out_trade_no", DateUtil.getFormatDate());
        data.put("device_info", "WEB");
        data.put("total_fee", "1");
        data.put("spbill_create_ip", IpUtil.getRemoteIp(request));
        data.put("notify_url", "/open/page/wxpayDone");
        data.put("trade_type", "JSAPI");
        data.put("openid", ((UserEntity) TokenManager.getWeixinToken()).getOpenid());

        Map<String, String> result = null;
        try {
            result = wxPay.unifiedOrder(data);

        } catch (Exception e) {

            LoggerUtil.error(getClass(), e.getMessage());
            e.printStackTrace();
        }
        return JSONArray.toJSON(result);
    }

    @RequestMapping("/wxpayDone")
    public String processResult(@RequestParam("return_code") String return_code,
                              @RequestParam("return_msg") String return_msg,
                              HttpServletRequest request) {

        String stringResult = null;

        if ("SUCCESS".equalsIgnoreCase(return_code)) {
            String result_code = request.getParameter("result_code");
            if ("SUCCESS".equalsIgnoreCase(result_code)) {
                // 成功将成功信息写进数据库，返回SUCCESS
                String openid = request.getParameter("openid");
                String transaction_id = request.getParameter("transaction_id");
                String out_trade_no = request.getParameter("out_trade_no");
                String now_date = request.getParameter("time_end");
                chargeService.charge(openid, transaction_id, out_trade_no, now_date);

                Map<String,String> result = new HashMap<>();
                result.put("return_code", "SUCCESS");
                result.put("return_msg", "OK");
                try {
                    stringResult=WXPayUtil.mapToXml(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return stringResult;

            } else {
                // 失败，返回FAIL
                String err_code = request.getParameter("err_code");
                String err_code_des = request.getParameter("err-code_des");
                Map<String, String> result = new HashMap<>();
                result.put("return_code", "FAIL");
                result.put("return_msg", err_code + "----" + err_code_des);
                try {
                    stringResult= WXPayUtil.mapToXml(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return stringResult;
            }
        }

        Map<String, String> result = new HashMap<>();
        result.put("return_code", "FAIL");
        result.put("return-msg", "支付失败，请联系管理员或者稍后重试 \n" + return_msg);
        try {
            stringResult = WXPayUtil.mapToXml(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringResult;
    }
}
