package com.outstudio.weixin.core.shiro.token;

import com.outstudio.weixin.common.po.ManagerEntity;
import com.outstudio.weixin.common.po.UserEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Created by 96428 on 2017/8/2.
 * This in electricity, io.github.cyingyo.electricity.core.shiro
 */
public class TokenManager {

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static Session getSession() {
        return getSubject().getSession();
    }

    public static UserEntity getWeixinToken() {
        return (UserEntity) getSubject().getPrincipal();
    }

    public static ManagerEntity getManagerToken() {
        return (ManagerEntity) getSubject().getPrincipal();
    }

    public static void loginBack(ManagerEntity manager, boolean rememberMe) {
        WeixinToken token = new WeixinToken();
        token.setAccount(manager.getM_account());
        token.setPassword(manager.getM_password());
        token.setWechat(false);
        token.setRememberMe(rememberMe);

        getSubject().login(token);
    }
    public static void loginWeixin(UserEntity user) {
        WeixinToken token = new WeixinToken();
        token.setWeixinOpenid(user.getOpenid());
        token.setWechat(true);
        token.setRememberMe(true);

        getSubject().login(token);
    }

    public static void logout() {
        getSubject().logout();
    }

    public static void save(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object get(Object key) {
        return getSession().getAttribute(key);
    }

    public static Object getAndRemove(Object key) {
        Object ans = getSession().getAttribute(key);
        getSession().removeAttribute(key);
        return ans;
    }
}
