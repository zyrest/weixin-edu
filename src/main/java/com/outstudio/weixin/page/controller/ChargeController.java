package com.outstudio.weixin.page.controller;

import com.outstudio.weixin.common.consts.ResponseStatus;
import com.outstudio.weixin.common.service.ChargeService;
import com.outstudio.weixin.common.vo.MessageVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by lmy on 2017/9/12.
 */
@RestController
@RequestMapping("/open/page")
public class ChargeController {
    private static final String REDIRECT_URL = "";

    @Resource
    private ChargeService chargeService;

    @PostMapping("/charge")
    public MessageVo charge(@RequestParam("openid") String openid) {
        MessageVo messageVo = new MessageVo();
        chargeService.charge(openid);
        messageVo.setStatus(ResponseStatus.CREATED)
                .setMessage("created")
                .setRedirectUrl(REDIRECT_URL);
        return messageVo;
    }
}
