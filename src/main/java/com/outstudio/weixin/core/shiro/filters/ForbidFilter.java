package com.outstudio.weixin.core.shiro.filters;

import com.outstudio.weixin.common.consts.ResponseStatus;
import com.outstudio.weixin.common.utils.LoggerUtil;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 96428 on 2017/9/7.
 * This in weixin-edu, com.outstudio.weixin.core.shiro.filters
 */
public class ForbidFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        LoggerUtil.fmtDebug(getClass(), "用户访问了不该被直接访问的页面");
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        if (ShiroFilterUtil.isAjax(servletRequest)) {
            Map<Object, Object> map = new HashMap<>();
            map.put("status", ResponseStatus.NO_PERMISSION);
            map.put("message", "该地址无法被访问!");
            ShiroFilterUtil.writeJsonToResponse(servletResponse, map);
            return false;
        }

        LoggerUtil.fmtDebug(getClass(), "重定向到 : /open/back/error");
        WebUtils.issueRedirect(servletRequest, servletResponse, "/open/back/error");
        return false;
    }
}
