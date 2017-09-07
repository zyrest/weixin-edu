package com.outstudio.weixin.common.dao;

import com.outstudio.weixin.common.po.InterviewVideoEntity;

public interface InterviewVideoEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InterviewVideoEntity record);

    int insertSelective(InterviewVideoEntity record);

    InterviewVideoEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InterviewVideoEntity record);

    int updateByPrimaryKey(InterviewVideoEntity record);
}