package com.outstudio.weixin.common.dao;

import com.outstudio.weixin.common.po.ManagerEntity;

public interface ManagerEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ManagerEntity record);

    int insertSelective(ManagerEntity record);

    ManagerEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ManagerEntity record);

    int updateByPrimaryKey(ManagerEntity record);
}