package com.outstudio.weixin.common.service;

import com.outstudio.weixin.common.dao.ManagerEntityMapper;
import com.outstudio.weixin.common.po.ManagerEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}
