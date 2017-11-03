package com.outstudio.weixin.common.dao;

import com.outstudio.weixin.common.po.EnReviewEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

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

    @Select(value = "SELECT stage from t_en_review")
    Set<Integer> selectStage();

    @Select(value = "SELECT COUNT(id) FROM t_en_review")
    Long selectCount();
}