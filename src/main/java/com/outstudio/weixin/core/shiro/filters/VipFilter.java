package com.outstudio.weixin.core.shiro.filters;

import com.outstudio.weixin.common.consts.ResponseStatus;
import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.common.utils.DateUtil;
import com.outstudio.weixin.common.utils.LoggerUtil;
import com.outstudio.weixin.core.shiro.token.TokenManager;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 96428 on 2017/9/6.
 * This in weixin-edu, com.outstudio.weixin.core.shiro.filters
 */
public class VipFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        UserEntity token = TokenManager.getWeixinToken();
        if (token != null && DateUtil.isNotExpire(token.getVip_end_date())) {
            return true;
        }

        LoggerUtil.fmtDebug(getClass(), "用户没有vip权限, 无法访问该资源");
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        if (ShiroFilterUtil.isAjax(servletRequest)) {
            Map<Object, Object> map = new HashMap<>();
            map.put("status", ResponseStatus.NO_PERMISSION);
            map.put("message", "用户没有权限访问该资源");
            ShiroFilterUtil.writeJsonToResponse(servletResponse, map);
            return false;
        }

        WebUtils.issueRedirect(servletRequest, servletResponse, "/open/page/unauth");
        return false;
    }
}
