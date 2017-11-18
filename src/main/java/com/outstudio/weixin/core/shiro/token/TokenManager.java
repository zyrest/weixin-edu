package com.outstudio.weixin.core.shiro.token;

import com.alibaba.fastjson.JSON;
import com.outstudio.weixin.common.po.ManagerEntity;
import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.common.utils.DateUtil;
import com.outstudio.weixin.common.utils.LoggerUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.Date;

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

    public static boolean isManager() {
        return getSubject().getPrincipal() instanceof ManagerEntity;
    }

    public static void loginBack(ManagerEntity manager, boolean rememberMe) {
        WeixinToken token = new WeixinToken();
        token.setAccount(manager.getM_account());
        token.setPass(manager.getM_password());
        token.setWechat(false);
        token.setRememberMe(rememberMe);

        getSubject().login(token);
    }
    public static void loginWeixin(UserEntity user) {
        WeixinToken token = new WeixinToken();
        token.setWeixinOpenid(user.getOpenid());
        token.setWechat(true);
        token.setRememberMe(false);

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

    public static boolean isVip() {
        if (getSubject().getPrincipal() == null) return false;
        if (getSubject().getPrincipal() instanceof ManagerEntity) {
            return true;
        } else {
            UserEntity user = getWeixinToken();

            LoggerUtil.fmtDebug(TokenManager.class, "isVip方法中的用户 %s",JSON.toJSONString(user));

            return DateUtil.isNotExpire(user.getVip_end_date());
        }
    }

    public static boolean isVip(UserEntity user) {
        return DateUtil.isNotExpire(user.getVip_end_date());
    }

    public static long tillDate() {
        if (!isVip()) return 0;
        else {
            UserEntity user;
            try {
                user = getWeixinToken();
            } catch (Exception e) {
                return 0;
            }
            Date until = user.getVip_end_date();

            return DateUtil.daysBetween(new Date(), until);
        }
    }

    public static long tillDate(UserEntity user) {
        return DateUtil.daysBetween(new Date(), user.getVip_end_date());
    }
}
