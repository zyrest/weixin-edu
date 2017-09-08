package com.outstudio.weixin.back.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 96428 on 2017/9/7.
 * This in weixin-edu, com.outstudio.weixin.back.controller
 */
@Controller
@RequestMapping("/open/back")
public class BackBaseController {

    @RequestMapping("/login")
    public ModelAndView login() {
        ModelAndView model = new ModelAndView();
        model.setViewName("hide/back/login");
        return model;
    }
    @RequestMapping("/error")
    public ModelAndView error() {
        ModelAndView model = new ModelAndView();
        model.setViewName("hide/back/error");
        return model;
    }

}
