package com.outstudio.weixin.back.controller;

import com.outstudio.weixin.common.consts.ResponseStatus;
import com.outstudio.weixin.common.service.ManagerService;
import com.outstudio.weixin.common.vo.MessageVo;
import com.outstudio.weixin.core.shiro.token.TokenManager;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController("/back")
public class ManagerController {

    @Resource
    private ManagerService managerService;

    @PutMapping("/sub/password")
    public MessageVo changePassword(@RequestBody Map<String, String> map) {
        String result = managerService.changePassword(map.get("newPassword"), map.get("oldPassword"));
        if ("success".equalsIgnoreCase(result))
            return new MessageVo().setMessage(result).setStatus(ResponseStatus.SUCCESS);
        else
            return new MessageVo().setMessage(result).setStatus(ResponseStatus.WRONG_TOKEN);
    }
}

