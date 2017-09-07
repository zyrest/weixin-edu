package com.outstudio.weixin.common.service;

import com.outstudio.weixin.common.dao.UserEntityMapper;
import com.outstudio.weixin.common.po.UserEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by 96428 on 2017/9/6.
 * This in weixin-edu, com.outstudio.weixin.common.service
 */
@Service
public class UserService {

    @Resource
    private UserEntityMapper userEntityMapper;

    public UserEntity getUserByOpenId(String openid) {
        return userEntityMapper.selectByPrimaryKey(openid);
    }

    public void saveUser(UserEntity user) {
        userEntityMapper.insert(user);
    }


}