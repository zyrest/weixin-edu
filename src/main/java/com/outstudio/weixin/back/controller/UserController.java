package com.outstudio.weixin.back.controller;

import com.outstudio.weixin.common.service.UserService;
import com.outstudio.weixin.common.utils.MessageVoUtil;
import com.outstudio.weixin.common.vo.MessageVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;

@RestController("/back")
public class UserController {

    @Resource
    UserService userService;

    @GetMapping("/users")
    public MessageVo getUsers() {
        return MessageVoUtil.success(userService.getAllUsers());
    }

    @PutMapping("/user/{id}")
    public MessageVo setAgent(@PathVariable("id") Integer id,
                              @RequestParam("level") Integer level) {
        return MessageVoUtil.created("", userService.setAgent(id, level));
    }

    @PutMapping("/user/{id}/balance")
    public MessageVo setBalance(@PathVariable("id") Integer id,
                                @RequestParam("balance") double balance) {
        return MessageVoUtil.created("", userService.setBalance(id, balance));
    }


}
