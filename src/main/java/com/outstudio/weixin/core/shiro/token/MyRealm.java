package com.outstudio.weixin.core.shiro.token;

import com.outstudio.weixin.common.po.ManagerEntity;
import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.common.service.ManagerService;
import com.outstudio.weixin.common.service.UserService;
import com.outstudio.weixin.common.utils.DateUtil;
import com.outstudio.weixin.common.utils.LoggerUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * Created by 96428 on 2017/8/2.
 * This in electricity, io.github.cyingyo.electricity.core.shiro
 */
public class MyRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Resource
    private ManagerService managerService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken) throws AuthenticationException {

        WeixinToken token = (WeixinToken) authenticationToken;

        if (token.isWechat()) {
            String weixinOpenid = token.getWeixinOpenid();
            LoggerUtil.fmtDebug(getClass(), "正在登陆微信，现在登陆的用户是 ：%s", weixinOpenid);

            UserEntity found = userService.getUserByOpenId(weixinOpenid);

            if (null == found) {
                throw new UnknownAccountException("用户名不存在");
            }

            LoggerUtil.fmtDebug(getClass(), "用户{%s}登陆成功", weixinOpenid);

            return new SimpleAuthenticationInfo(found, "", getName());
        } else {
            String account = token.getAccount();
            ManagerEntity found = managerService.getByAccount(account);

            if (null == found) {
                throw new UnknownAccountException("用户名不存在");
            }

            return new SimpleAuthenticationInfo(found, found.getM_password(), getName());
        }

    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        Object principal = principalCollection.getPrimaryPrincipal();
        if (principal instanceof UserEntity) {
            UserEntity user = (UserEntity) principal;

            if (DateUtil.isNotExpire(user.getVip_end_date())) {
                authorizationInfo.addRole("vip");
            } else {
                authorizationInfo.addRole("user");
            }
        } else {
            ManagerEntity manager = (ManagerEntity) principal;
        }

        LoggerUtil.fmtDebug(getClass(), "授权成功");

        return authorizationInfo;
    }


}
