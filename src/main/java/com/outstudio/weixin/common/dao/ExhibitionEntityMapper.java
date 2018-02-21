package com.outstudio.weixin.common.dao;

import com.outstudio.weixin.common.po.ExhibitionEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ExhibitionEntityMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ExhibitionEntity record);

    int insertSelective(ExhibitionEntity record);

    @Select("select * from t_exhibition where id = #{id}")
    ExhibitionEntity selectByPrimaryKet(@Param("id") Integer id);

    ExhibitionEntity selectByPrimaryKey(Integer id, Integer verified);

    int updateByPrimaryKeySelective(ExhibitionEntity record);

    int updateByPrimaryKey(ExhibitionEntity record);

    @Select("select * from t_exhibition where verified = #{verified}")
    List<ExhibitionEntity> selectByVerified(@Param("verified") Integer verified);

    @Select("select * from t_exhibition where type = #{type} and verified = #{verified}")
    List<ExhibitionEntity> selectAllByType(@Param("type") String type, @Param("verified") Integer verified);

    @Select("select * from t_exhibition where title like #{searchParam} or description like #{searchParam} and verified = #{verified}")
    List<ExhibitionEntity> selectBySearchParam(@Param("searchParam") String searchParam, @Param("verified") Integer verified);

    @Select("select count(*) from t_exhibition where type = #{type}")
    int count(@Param("type") String type);
}