package com.outstudio.weixin.common.service;

import com.github.pagehelper.PageHelper;
import com.outstudio.weixin.common.dao.ManagerEntityMapper;
import com.outstudio.weixin.common.po.ManagerEntity;
import com.outstudio.weixin.common.utils.PasswordUtil;
import com.outstudio.weixin.core.shiro.token.TokenManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 96428 on 2017/9/7.
 * This in weixin-edu, com.outstudio.weixin.common.service
 */
@Service
public class ManagerService {

    @Resource
    private ManagerEntityMapper managerEntityMapper;

    public ManagerEntity getByAccount(String account) {
        return managerEntityMapper.selectByAccount(account);
    }

    public String changePassword(String newPassword, String oldPassword) {
        ManagerEntity managerEntity = TokenManager.getManagerToken();
        boolean flag = PasswordUtil.validatePassword(oldPassword, managerEntity.getM_password());
        if (!flag) {
            return "old password is not right";
        } else {
            int resultCode = setPassword(managerEntity.getM_account(), newPassword);
            if (resultCode != 0) {
                return "success";
            } else {
                return "fail";
            }
        }
    }

    private int setPassword(String account, String password) {
        String encodedPass = PasswordUtil.createHash(password);
        return managerEntityMapper.setPassword(encodedPass, account);
    }
}
