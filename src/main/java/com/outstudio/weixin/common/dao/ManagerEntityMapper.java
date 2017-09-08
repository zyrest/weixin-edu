package com.outstudio.weixin.common.dao;

import com.outstudio.weixin.common.po.ManagerEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ManagerEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ManagerEntity record);

    int insertSelective(ManagerEntity record);

    ManagerEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ManagerEntity record);

    int updateByPrimaryKey(ManagerEntity record);

    @Select(value = "select * from t_manager where m_account = #{account}")
    ManagerEntity selectByAccount(@Param("account") String account);
}