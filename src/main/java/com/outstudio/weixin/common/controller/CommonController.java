package com.outstudio.weixin.common.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 96428 on 2017/9/6.
 * This in weixin-edu, com.outstudio.weixin.common.controller
 */
@RestController
public class CommonController {

    @GetMapping("/hello")
    public String hello() {
        return "hello world!";
    }
}
