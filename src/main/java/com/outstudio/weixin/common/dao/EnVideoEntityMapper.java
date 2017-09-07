package com.outstudio.weixin.common.dao;

import com.outstudio.weixin.common.po.EnVideoEntity;

public interface EnVideoEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnVideoEntity record);

    int insertSelective(EnVideoEntity record);

    EnVideoEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnVideoEntity record);

    int updateByPrimaryKey(EnVideoEntity record);
}