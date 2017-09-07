package com.outstudio.weixin.core.shiro.filters;


import com.outstudio.weixin.common.consts.ResponseStatus;
import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.common.utils.LoggerUtil;
import com.outstudio.weixin.core.shiro.token.TokenManager;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 96428 on 2017/7/25.
 * This in ssmjavaconfig, samson.core.shiro.filter
 */
public class LoginFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse,
                                      Object o) throws Exception {

        UserEntity token = TokenManager.getToken();
        if (token != null || ShiroFilterUtil.isOpen(servletRequest)) {
            return Boolean.TRUE;
        }

        LoggerUtil.fmtDebug(getClass(), "未登陆用户！");


        return Boolean.TRUE;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest,
                                     ServletResponse servletResponse) throws Exception {

        if (ShiroFilterUtil.isAjax(servletRequest)) {
            Map<Object, Object> map = new HashMap<>();
            map.put("status", ResponseStatus.NO_PERMISSION);
            map.put("message", "用户未登录");
            ShiroFilterUtil.writeJsonToResponse(servletResponse, map);
            return Boolean.FALSE;
        }

        //保存Request和Response 到登录后的链接
        saveRequest(servletRequest);

        WebUtils.issueRedirect(servletRequest, servletResponse, ShiroFilterUtil.LOGIN_URL);

        LoggerUtil.fmtDebug(getClass(),
                "目前访问[%s],正在前往登录界面", ShiroFilterUtil.getUrl(servletRequest));
        return Boolean.FALSE;
    }
}
