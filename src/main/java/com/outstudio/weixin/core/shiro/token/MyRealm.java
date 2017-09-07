package com.outstudio.weixin.core.shiro.token;

import com.outstudio.weixin.common.po.UserEntity;
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

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken) throws AuthenticationException {

        WeixinToken token = (WeixinToken) authenticationToken;

        String weixinOpenid = token.getWeixinOpenid();
        LoggerUtil.fmtDebug(getClass(), "正在登陆，现在登陆的用户是 ：%s", weixinOpenid);

        UserEntity found = userService.getUserByOpenId(weixinOpenid);

        if (null == found) {
            throw new UnknownAccountException("用户名不存在");
        }

        LoggerUtil.fmtDebug(getClass(), "用户{%s}登陆成功", weixinOpenid);

        return new SimpleAuthenticationInfo(found, "123456", getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        UserEntity user = (UserEntity) principalCollection.getPrimaryPrincipal();
        String openid = user.getOpenid();

        if (DateUtil.isNotExpire(user.getVip_end_date())) {
            authorizationInfo.addRole("vip");
        } else {
            authorizationInfo.addRole("user");
        }

        LoggerUtil.fmtDebug(getClass(), "授权成功");

        return authorizationInfo;
    }


}
