package com.outstudio.weixin.page.controller;

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
    public Map<String, String> pay(HttpServletRequest request,
                                   @RequestParam("fee") String fee,
                                   @RequestParam("pid") Integer pid) {

        init();

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("body", "vip充值");
        data.put("out_trade_no", DateUtil.getFormatDate());
        data.put("device_info", "WEB");
        data.put("total_fee", fee);
        data.put("spbill_create_ip", request.getRemoteAddr());
        data.put("notify_url", "/open/page/wxpayDone");
        data.put("trade_type", "JSAPI");
        data.put("openid", TokenManager.getWeixinToken().getOpenid());

        LoggerUtil.fmtDebug(getClass(), "支付信息", data.toString());

        Map<String, String> result = null;
        try {
            result = wxPay.unifiedOrder(data);
            result = processPayResult(result, fee, pid);
        } catch (Exception e) {
            LoggerUtil.error(getClass(), e.getMessage());
            e.printStackTrace();
        }
        return result;
    }


    private Map<String, String> processPayResult(Map<String, String> result, String fee, Integer pid) {
        Map<String, String> processResult = new HashMap<String, String>();
        if ("SUCCESS".equalsIgnoreCase(result.get("return_code"))) {
            if ("SUCCESS".equalsIgnoreCase(result.get("result_code"))) {
                processResult.put("trade_type", result.get("trade_type"));
                processResult.put("prepay_id", result.get("prepay_id"));
                processResult.put("result", "success");
                if (pid == null)
                    chargeService.charge(TokenManager.getWeixinToken().getOpenid(), DateUtil.getFormatDate(), new Date(), fee);
                else
                    chargeService.charge(TokenManager.getWeixinToken().getOpenid(), DateUtil.getFormatDate(), new Date(), fee, pid);
                return processResult;
            } else {
                processResult.put("result_code", result.get("result_code"));
                processResult.put("err_code", result.get("err_code"));
                processResult.put("err_code_des", result.get("err_code_des"));
                return processResult;
            }
        } else {
            processResult.put("return_code", result.get("return_code"));
            processResult.put("return_msg", result.get("return_msg"));
            return processResult;
        }
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
                String total_fee = request.getParameter("total_fee");

                 chargeService.charge(openid, transaction_id);

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
                String err_code = request.getParameter("err_code");
                String err_code_des = request.getParameter("err-code_des");
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
