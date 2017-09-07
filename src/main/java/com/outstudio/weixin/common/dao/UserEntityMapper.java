package com.outstudio.weixin.common.dao;

import com.outstudio.weixin.common.po.UserEntity;

public interface UserEntityMapper {
    int deleteByPrimaryKey(String openid);

    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    UserEntity selectByPrimaryKey(String openid);

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByPrimaryKey(UserEntity record);
}