package com.outstudio.weixin.page.controller;

import com.outstudio.weixin.common.consts.ResponseStatus;
import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.common.service.UserService;
import com.outstudio.weixin.common.utils.MessageVoUtil;
import com.outstudio.weixin.common.vo.MessageVo;
import com.outstudio.weixin.core.shiro.token.TokenManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by lmy on 2017/9/12.
 */
@Controller
@RequestMapping("/page")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/view/userInfo")
    public ModelAndView getUserInfo() {
        ModelAndView view = new ModelAndView();

        UserEntity user = TokenManager.getWeixinToken();
//        UserEntity user = new UserEntity();
//        user.setNickname("zhangsan");
//        user.setOpenid("asdasdads");
//        user.setId(1);
        view.addObject("user", user);
        view.addObject("isVip", TokenManager.isVip());
        view.addObject("tillDate",TokenManager.tillDate());

        view.addObject("balance", user.getBalance());
        view.addObject("people", userService.getCountsByPid(user.getId()));
        view.addObject("level", user.getLevel());
        view.setViewName("hide/page/personalCentre");
        return view;
    }
}
