package com.outstudio.weixin.core.shiro.filters;

import com.outstudio.weixin.common.po.ManagerEntity;
import com.outstudio.weixin.core.shiro.token.TokenManager;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class AlreadyLoginFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        ManagerEntity token = TokenManager.getManagerToken();

        return token == null;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        WebUtils.issueRedirect(servletRequest, servletResponse, "/back/index");
        return false;
    }
}
