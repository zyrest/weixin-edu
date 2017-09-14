package com.outstudio.weixin.page.controller;

import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.common.service.UserService;
import com.outstudio.weixin.common.utils.LoggerUtil;
import com.outstudio.weixin.common.utils.OAuthUtil;
import com.outstudio.weixin.common.utils.StringUtil;
import com.outstudio.weixin.core.shiro.token.TokenManager;
import com.outstudio.weixin.wechat.dto.OAuthAccessToken;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 96428 on 2017/9/14.
 * This in weixin-edu, com.outstudio.weixin.page.controller
 */
@Controller
@RequestMapping("/open/page")
public class AuthController {

    @Resource
    private UserService userService;

    @RequestMapping("/oauth")
    public String oauth(HttpServletRequest request,
                      @RequestParam("code") String code,
                      @RequestParam("state") String state) {

        OAuthAccessToken accessToken = OAuthUtil.getOAuthAccessToken(code);
        String openid = accessToken.getOpenid();
        UserEntity user = userService.getUserByOpenId(openid);
        if (user == null) {
            user = OAuthUtil.getUserInfo(accessToken);
            userService.saveUser(user);
        }
        TokenManager.loginWeixin(user);

        String url = "/open/page/index";
        SavedRequest savedRequest = WebUtils.getSavedRequest(request);
        if (null != savedRequest && !StringUtil.isBlank(savedRequest.getRequestUrl())) {
            url = savedRequest.getRequestUrl();
        }
        LoggerUtil.debug(getClass(), "用户授权登陆成功，返回url为 : " + url);

        return "redirect:" + url;
    }
}
