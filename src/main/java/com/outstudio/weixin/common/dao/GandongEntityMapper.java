package com.outstudio.weixin.common.dao;

import com.outstudio.weixin.common.po.GandongEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GandongEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GandongEntity record);

    int insertSelective(GandongEntity record);

    GandongEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GandongEntity record);

    int updateByPrimaryKey(GandongEntity record);

    @Select(value = "SELECT * FROM t_gandong")
    List<GandongEntity> selectAll();

    @Select(value = "SELECT COUNT(id) FROM t_gandong")
    Long selectCount();
}