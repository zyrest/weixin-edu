package com.outstudio.weixin.common.dao;

import com.outstudio.weixin.common.po.EnReviewEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

public interface EnReviewEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnReviewEntity record);

    int insertSelective(EnReviewEntity record);

    EnReviewEntity selectByPrimaryKey(Integer id);

    List<EnReviewEntity> selectByStage(Integer stage,String type);

    List<EnReviewEntity> selectAll(String type);

    List<EnReviewEntity> selectBySearchParam(String searchParam,String type);

    int updateByPrimaryKeySelective(EnReviewEntity record);

    int updateByPrimaryKeyWithBLOBs(EnReviewEntity record);

    int updateByPrimaryKey(EnReviewEntity record);

    @Select(value = "SELECT stage from t_en_review where type=#{type}")
    Set<Integer> selectStage(@Param("type")String type);

    @Select(value = "SELECT COUNT(id) FROM t_en_review where type=#{type}")
    Long selectCount(@Param("type")String type);
}