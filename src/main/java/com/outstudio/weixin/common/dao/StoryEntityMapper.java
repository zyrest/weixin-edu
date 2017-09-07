package com.outstudio.weixin.common.dao;

import com.outstudio.weixin.common.po.StoryEntity;

public interface StoryEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StoryEntity record);

    int insertSelective(StoryEntity record);

    StoryEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StoryEntity record);

    int updateByPrimaryKey(StoryEntity record);
}