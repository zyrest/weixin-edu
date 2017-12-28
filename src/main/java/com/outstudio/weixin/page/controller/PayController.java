package com.outstudio.weixin.page.controller;

import com.outstudio.weixin.page.service.PayService;
import com.outstudio.weixin.wechat.utils.MessageUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by lmy on 2017/9/16.
 */
@RestController
@RequestMapping("/open/page")
public class PayController {


    @Resource
    private PayService payService;

    @RequestMapping("/wxpay")
    public Map<String, String> pay(HttpServletRequest request,
                                   @RequestParam("fee") String fee) {

        String ip = request.getRemoteAddr();

        Map<String, String> result = payService.doPay(fee, ip);

        return payService.getH5PayParams(result);
    }

    @RequestMapping("/wxpayDone")
    public String processResult(HttpServletRequest request) throws Exception {

        Map<String, String> map = MessageUtil.xml2Map(request);

        return payService.callBack(map);
    }
}
