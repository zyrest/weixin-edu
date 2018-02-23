package com.outstudio.weixin.page.controller;

import com.alibaba.fastjson.JSON;
import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.common.service.UserService;
import com.outstudio.weixin.common.utils.LoggerUtil;
import com.outstudio.weixin.common.vo.VipType;
import com.outstudio.weixin.core.shiro.token.MyRealm;
import com.outstudio.weixin.core.shiro.token.TokenManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @Resource
    private MyRealm myRealm;

    @GetMapping("/view/userInfo")
    public ModelAndView getUserInfo() {
        ModelAndView view = new ModelAndView();

        UserEntity user = TokenManager.getWeixinToken();
        int userId = user.getId();

        // For Test
//        int userId = 2;

        UserEntity gotUser = userService.getUserById(userId);
        LoggerUtil.fmtDebug(getClass(), "数据库中的用户，%s", JSON.toJSONString(gotUser));
        LoggerUtil.fmtDebug(getClass(), "开始重新登陆！");

        myRealm.clearCached();
        TokenManager.logout();
        TokenManager.loginWeixin(gotUser);

        LoggerUtil.fmtDebug(getClass(), "重新登陆成功！！");
        LoggerUtil.fmtDebug(getClass(), "目前缓存中的用户, %s", JSON.toJSONString(TokenManager.getWeixinToken()));

        view.addObject("user", gotUser);
        view.addObject("isEnglishVip", TokenManager.isVip(VipType.ENGLISH.getName()));
        view.addObject("EnglishVipTillDate", TokenManager.tillDate(VipType.ENGLISH.getName()));
        view.addObject("isMathVip", TokenManager.isVip(VipType.MATH.getName()));
        view.addObject("MathVipTillDate", TokenManager.tillDate(VipType.MATH.getName()));
        view.addObject("isPhysicsVip", TokenManager.isVip(VipType.PHYSICS.getName()));
        view.addObject("PhysicsVipTillDate", TokenManager.tillDate(VipType.PHYSICS.getName()));
        view.addObject("isChemistryVip", TokenManager.isVip(VipType.CHEMISTRY.getName()));
        view.addObject("ChemistryVipTillDate", TokenManager.tillDate(VipType.CHEMISTRY.getName()));

        view.addObject("balance", gotUser.getBalance());
        view.addObject("people", userService.getCountsByPid(gotUser.getId()));
        view.addObject("level", gotUser.getLevel());

        LoggerUtil.fmtDebug(getClass(), JSON.toJSONString(view.getModel()));

        view.setViewName("hide/page/personalCentre");
        return view;
    }
}
