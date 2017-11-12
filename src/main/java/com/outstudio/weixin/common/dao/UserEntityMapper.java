package com.outstudio.weixin.common.dao;

import com.outstudio.weixin.common.po.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserEntityMapper {
    int deleteByPrimaryKey(String openid);

    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    UserEntity selectByPrimaryKey(String openid);

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByPrimaryKey(UserEntity record);

    @Select("select id,pid,balance,vip_end_date,level,nickname from t_user")
    List<UserEntity> getAllUsers();

    @Update("update t_user set level=#{level} where id = #{id}")
    int setAgent(Integer id, Integer level);

    @Update("update t_user set balance=#{balance} where id = #{id}")
    int setBalance(@Param("id") Integer id, @Param("balance") double balance);

    @Select("select id,pid,balance,vip_end_date,level,nickname from t_user where id=#{id}")
    UserEntity getUserById(@Param("id") Integer id);
}