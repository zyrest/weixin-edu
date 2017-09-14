package com.outstudio.weixin.common.dao;

import com.outstudio.weixin.common.po.WelcomeEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by lmy on 2017/9/14.
 */
public interface WelcomeEntityMapper {
    @Delete("delete from t_welcome where id=#{id}")
    int deleteByPrimaryKey(@Param("id") Integer id);

    @Insert("insert into t_welcome(content,isUsing) values(#{record.content},#{record.isUsing})")
    int insert(@Param("record") WelcomeEntity record);

    @Select("select id,content,isUsing from t_welcome where id=#{id}")
    WelcomeEntity selectByPrimaryKey(@Param("id") Integer id);

    @Select("select id,content,isUsing from t_welcome")
    List<WelcomeEntity> selectAll();

    @Select("select id,content,isUsing from t_welcome where isUsing=#{isUsing}")
    WelcomeEntity selectByUsing(@Param("isUsing") Integer isUsing);

    @Update("update t_welcome set content=#{record.content} where id=#{record.id}")
    int updateByPrimaryKey(@Param("record") WelcomeEntity record);

    @Update("update t_welcome set isUsing=#{isUsing} where id=#{id}")
    int updateIsUsingById(@Param("id") Integer id,@Param("isUsing") Integer isUsing);

}
