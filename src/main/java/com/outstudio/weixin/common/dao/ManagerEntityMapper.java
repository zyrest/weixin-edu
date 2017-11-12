package com.outstudio.weixin.common.dao;

import com.outstudio.weixin.common.po.ManagerEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ManagerEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ManagerEntity record);

    int insertSelective(ManagerEntity record);

    ManagerEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ManagerEntity record);

    int updateByPrimaryKey(ManagerEntity record);

    @Select(value = "select * from t_manager where m_account = #{account}")
    ManagerEntity selectByAccount(@Param("account") String account);

    @Update("update t_manager set m_password = #{password} where m_account=#{account}")
    int setPassword(@Param("password") String password, @Param("account") String account);
}