package com.outstudio.weixin.common.dao;

import com.outstudio.weixin.common.po.UpvoteEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UpvoteEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UpvoteEntity record);

    int insertSelective(UpvoteEntity record);

    UpvoteEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UpvoteEntity record);

    int updateByPrimaryKey(UpvoteEntity record);

    @Select("select * from t_upvote where exhibition_id = #{exhibition_id} and openid = #{openid}")
    UpvoteEntity selectByExhibitionIdAndOpenid(@Param("exhibition_id") Integer exhibition_id, @Param("openid") String openid);
}