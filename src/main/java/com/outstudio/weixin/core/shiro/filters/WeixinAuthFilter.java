package com.outstudio.weixin.core.shiro.filters;

import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.core.shiro.token.TokenManager;
import com.outstudio.weixin.wechat.config.WeixinProperties;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by 96428 on 2017/9/14.
 * This in weixin-edu, com.outstudio.weixin.core.shiro.filters
 */
public class WeixinAuthFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {

        if (TokenManager.isManager()) {
            return true;
        }
        UserEntity user = TokenManager.getWeixinToken();
        return user != null;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        saveRequest(servletRequest);
        String url = String.format(WeixinProperties.USER_INFO_CODE,
                WeixinProperties.appID,
                WeixinProperties.OAUTH_REDIRECT_URL,
                WeixinProperties.USERINFO_SCOPE,
                "");
        WebUtils.issueRedirect(servletRequest, servletResponse, url);

        return false;
    }
}
