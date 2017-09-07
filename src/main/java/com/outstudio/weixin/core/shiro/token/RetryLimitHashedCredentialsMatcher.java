package com.outstudio.weixin.core.shiro.token;

import com.outstudio.weixin.common.utils.PasswordUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 96428 on 2017/8/4.
 * This in electricity, io.github.cyingyo.electricity.core.shiro.token
 */
public class RetryLimitHashedCredentialsMatcher implements CredentialsMatcher {

    private Cache passwordRetryCache;

    @Resource
    private EhCacheCacheManager ehCacheCacheManager;

    private void init() {
        if (passwordRetryCache == null) {
            passwordRetryCache = ehCacheCacheManager.getCache("passwordRetryCache");
        }
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {

        init();

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        String userName = token.getUsername();
        AtomicInteger count = passwordRetryCache.get(userName, AtomicInteger.class);
        if (count == null) {
            count = new AtomicInteger(0);
            passwordRetryCache.put(userName, count);
        }
        if (count.incrementAndGet() > 5) {
            //尝试次数大于5次，抛出异常
            throw new ExcessiveAttemptsException("密码错误次数过多！请十分钟后再试！");
        }

        String encodePassword = authenticationInfo.getCredentials().toString();
        String password = new String(token.getPassword());

        boolean flag = PasswordUtil.validatePassword(password, encodePassword);
        if (flag) {
            passwordRetryCache.evict(userName);
        }

        return flag;
    }
}
