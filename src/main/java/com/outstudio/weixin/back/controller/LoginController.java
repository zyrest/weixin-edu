package com.outstudio.weixin.back.controller;

import com.outstudio.weixin.back.exception.NoSuchUserException;
import com.outstudio.weixin.back.exception.SystemErrorException;
import com.outstudio.weixin.back.exception.WrongPasswordException;
import com.outstudio.weixin.common.consts.ResponseStatus;
import com.outstudio.weixin.common.po.ManagerEntity;
import com.outstudio.weixin.common.utils.LoggerUtil;
import com.outstudio.weixin.common.utils.MessageVoUtil;
import com.outstudio.weixin.common.utils.StringUtil;
import com.outstudio.weixin.common.vo.MessageVo;
import com.outstudio.weixin.core.shiro.token.TokenManager;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 96428 on 2017/9/8.
 * This in weixin-edu, com.outstudio.weixin.back.controller
 */
@Controller
public class LoginController {

    @PostMapping("/open/back/sublogin")
    @ResponseBody
    public MessageVo sublogin(HttpServletRequest request, @RequestBody ManagerEntity managerEntity) {

        try {
            TokenManager.loginBack(managerEntity, false);
        } catch (UnknownAccountException e) {
            LoggerUtil.fmtDebug(getClass(), "用户名不存在 -> {%s}", managerEntity.getM_account());
            throw new NoSuchUserException(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            LoggerUtil.fmtDebug(getClass(), "用户密码错误 -> {%s}", managerEntity.getM_account());
            throw new WrongPasswordException("用户密码错误");
        }

        MessageVo messageVo = new MessageVo();
        //判断是否由其他页面跳转过来
        String url = "/back/index";
        SavedRequest savedRequest = WebUtils.getSavedRequest(request);
        if (null != savedRequest && !StringUtil.isBlank(savedRequest.getRequestUrl())) {
            url = savedRequest.getRequestUrl();
        }

        LoggerUtil.debug(getClass(), "返回url为 : " + url);

        managerEntity = TokenManager.getManagerToken();

        messageVo.setStatus(ResponseStatus.SUCCESS).setMessage("success")
                .setRedirectUrl(url).setData(managerEntity);
        return messageVo;
    }

    @RequestMapping("/back/logout")
    public ModelAndView logout() {
        ModelAndView view = new ModelAndView();

        try {
            TokenManager.logout();
            view.setViewName("redirect:/open/back/login");
        } catch (Exception e) {
            view.setViewName("redirect:/error");
            throw new SystemErrorException();
        }

        return view;
    }
}
