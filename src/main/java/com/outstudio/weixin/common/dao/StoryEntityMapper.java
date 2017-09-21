package com.outstudio.weixin.common.dao;

import com.outstudio.weixin.common.po.StoryEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StoryEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StoryEntity record);

    int insertSelective(StoryEntity record);

    StoryEntity selectByPrimaryKey(Integer id);

    List<StoryEntity> selectByType(Integer id);

    List<StoryEntity> selectAll();

    List<StoryEntity> selectBySearchParam(String searchParam);

    int updateByPrimaryKeySelective(StoryEntity record);

    int updateByPrimaryKey(StoryEntity record);

    @Select(value = "SELECT COUNT(id) FROM t_story")
    Long selectCount();
}