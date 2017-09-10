package com.outstudio.weixin.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 96428 on 2017/9/8.
 * This in weixin-edu, com.outstudio.weixin.page.controller
 */
@Controller
@RequestMapping("/open/page")
public class PageBaseController {

    @RequestMapping("/unauth")
    public ModelAndView unauth() {
        ModelAndView model = new ModelAndView();
        model.setViewName("hide/page/noPermission");
        return model;
    }
}
