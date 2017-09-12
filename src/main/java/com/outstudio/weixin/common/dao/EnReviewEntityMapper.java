package com.outstudio.weixin.common.dao;

import com.outstudio.weixin.common.po.EnReviewEntity;

import java.util.List;

public interface EnReviewEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnReviewEntity record);

    int insertSelective(EnReviewEntity record);

    EnReviewEntity selectByPrimaryKey(Integer id);

    List<EnReviewEntity> selectByStage(Integer stage);

    List<EnReviewEntity> selectAll();

    List<EnReviewEntity> selectBySearchParam(String searchParam);

    int updateByPrimaryKeySelective(EnReviewEntity record);

    int updateByPrimaryKeyWithBLOBs(EnReviewEntity record);

    int updateByPrimaryKey(EnReviewEntity record);
}