package com.outstudio.weixin.common.dao;

import com.outstudio.weixin.common.po.EnReviewEntity;

public interface EnReviewEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnReviewEntity record);

    int insertSelective(EnReviewEntity record);

    EnReviewEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnReviewEntity record);

    int updateByPrimaryKeyWithBLOBs(EnReviewEntity record);

    int updateByPrimaryKey(EnReviewEntity record);
}