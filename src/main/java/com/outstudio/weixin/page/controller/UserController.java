package com.outstudio.weixin.page.controller;

import com.outstudio.weixin.common.consts.ResponseStatus;
import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.common.service.UserService;
import com.outstudio.weixin.common.vo.MessageVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by lmy on 2017/9/12.
 */
@RestController
@RequestMapping("/open/page")
public class UserController {
    private static final String REDIRECT_URL = "";

    @Resource
    private UserService userService;

    @GetMapping("/userInfo/{openid}")
    public MessageVo getUserInfo(@PathVariable("openid") String openid) {
        MessageVo messageVo = new MessageVo();
        UserEntity userEntity = userService.getUserByOpenId(openid);
        messageVo.setStatus(ResponseStatus.SUCCESS)
                .setMessage("success")
                .setRedirectUrl(REDIRECT_URL)
                .setData(userEntity);
        return messageVo;
    }
}
