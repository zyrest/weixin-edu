package com.outstudio.weixin.common.dao;

import com.outstudio.weixin.common.po.ChargeEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ChargeEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChargeEntity record);

    int insertSelective(ChargeEntity record);

    ChargeEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChargeEntity record);

    int updateByOpenidSelective(ChargeEntity record);

    int updateByPrimaryKey(ChargeEntity record);

    @Select("select * from t_charge where openid=#{openid}")
    ChargeEntity getByOpenid(@Param("openid") String openid);
}