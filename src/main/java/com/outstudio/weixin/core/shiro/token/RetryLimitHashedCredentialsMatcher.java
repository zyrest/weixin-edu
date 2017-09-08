package com.outstudio.weixin.core.shiro.token;

import com.outstudio.weixin.common.utils.PasswordUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import javax.annotation.Resource;

/**
 * Created by 96428 on 2017/8/4.
 * This in electricity, io.github.cyingyo.electricity.core.shiro.token
 */
public class RetryLimitHashedCredentialsMatcher implements CredentialsMatcher {

    @Resource
    private EhCacheCacheManager ehCacheCacheManager;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {

        WeixinToken token = (WeixinToken) authenticationToken;

        if (token.isWechat()) {
            return true;
        } else {

            String encodePassword = authenticationInfo.getCredentials().toString();
            String password = token.getPass();

            return PasswordUtil.validatePassword(password, encodePassword);
        }
    }
}
