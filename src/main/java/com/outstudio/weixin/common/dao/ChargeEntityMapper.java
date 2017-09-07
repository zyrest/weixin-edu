package com.outstudio.weixin.common.dao;

import com.outstudio.weixin.common.po.ChargeEntity;

public interface ChargeEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChargeEntity record);

    int insertSelective(ChargeEntity record);

    ChargeEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChargeEntity record);

    int updateByPrimaryKey(ChargeEntity record);
}