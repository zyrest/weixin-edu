package com.outstudio.weixin.common.dao;

import com.outstudio.weixin.common.po.InterviewVideoEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface InterviewVideoEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InterviewVideoEntity record);

    int insertSelective(InterviewVideoEntity record);

    InterviewVideoEntity selectByPrimaryKey(Integer id);

    List<InterviewVideoEntity> selectAll();

    List<InterviewVideoEntity> selectBySearchParam(String searchParam);

    List<InterviewVideoEntity> selectByStage(Integer stage);

    int updateByPrimaryKeySelective(InterviewVideoEntity record);

    int updateByPrimaryKey(InterviewVideoEntity record);

    @Select(value = "SELECT COUNT(id) from t_interview_video")
    Long selectCount();
}