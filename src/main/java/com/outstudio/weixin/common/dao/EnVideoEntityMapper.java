package com.outstudio.weixin.common.dao;

import com.outstudio.weixin.common.po.EnVideoEntity;

import java.util.List;

public interface EnVideoEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnVideoEntity record);

    int insertSelective(EnVideoEntity record);

    EnVideoEntity selectByPrimaryKey(Integer id);

    List<EnVideoEntity> selectAll();

    List<EnVideoEntity> selectByStage(Integer stage);

    List<EnVideoEntity> selectBySearchParam(String searchParam);

    int updateByPrimaryKeySelective(EnVideoEntity record);

    int updateByPrimaryKey(EnVideoEntity record);
}