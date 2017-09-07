package com.outstudio.weixin.common.dao;

import com.outstudio.weixin.common.po.PointsEntity;

public interface PointsEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PointsEntity record);

    int insertSelective(PointsEntity record);

    PointsEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PointsEntity record);

    int updateByPrimaryKey(PointsEntity record);
}