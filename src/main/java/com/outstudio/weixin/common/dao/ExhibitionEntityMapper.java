package com.outstudio.weixin.common.dao;

import com.outstudio.weixin.common.po.ExhibitionEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ExhibitionEntityMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ExhibitionEntity record);

    int insertSelective(ExhibitionEntity record);

    ExhibitionEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ExhibitionEntity record);

    int updateByPrimaryKey(ExhibitionEntity record);

    @Select("select * from t_exhibition where type = #{type}")
    List<ExhibitionEntity> selectAllByType(@Param("type") String type);

    @Select("select * from t_exhibition where title like #{searchParam} or description like #{searchParam}")
    List<ExhibitionEntity> selectBySearchParam(@Param("searchParam") String searchParam);

    @Select("select count(*) from t_exhibition where type = #{type}")
    int count(@Param("type") String type);
}