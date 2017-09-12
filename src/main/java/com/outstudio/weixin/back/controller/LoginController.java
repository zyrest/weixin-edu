package com.outstudio.weixin.back.controller;

import com.outstudio.weixin.back.exception.NoSuchUserException;
import com.outstudio.weixin.back.exception.WrongPasswordException;
import com.outstudio.weixin.common.consts.ResponseStatus;
import com.outstudio.weixin.common.po.ManagerEntity;
import com.outstudio.weixin.common.utils.LoggerUtil;
import com.outstudio.weixin.common.utils.StringUtil;
import com.outstudio.weixin.common.vo.MessageVo;
import com.outstudio.weixin.core.shiro.token.TokenManager;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 96428 on 2017/9/8.
 * This in weixin-edu, com.outstudio.weixin.back.controller
 */
@RestController
@RequestMapping("/open/back")
public class LoginController {

    @PostMapping("/sublogin")
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
        String url = "/open/back/index";
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


}
