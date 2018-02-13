package com.outstudio.weixin.common.dao;

import com.outstudio.weixin.common.po.EnVideoEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

public interface EnVideoEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnVideoEntity record);

    int insertSelective(EnVideoEntity record);

    EnVideoEntity selectByPrimaryKey(Integer id);

    List<EnVideoEntity> selectAll(String type);

    List<EnVideoEntity> selectByStage(Integer stage,String type);

    List<EnVideoEntity> selectBySearchParam(String searchParam, String type);

    int updateByPrimaryKeySelective(EnVideoEntity record);

    int updateByPrimaryKey(EnVideoEntity record);

    @Select(value = "SELECT stage from t_en_video where type=#{type}")
    Set<Integer> selectStage(@Param("type") String type);

    @Select(value = "SELECT COUNT(id) FROM t_en_video where type=#{type}")
    Long selectCount(@Param("type") String type);
}