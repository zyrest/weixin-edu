package com.outstudio.weixin.common.dao;

import com.outstudio.weixin.common.po.MessageEntity;

public interface MessageEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MessageEntity record);

    int insertSelective(MessageEntity record);

    MessageEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MessageEntity record);

    int updateByPrimaryKeyWithBLOBs(MessageEntity record);

    int updateByPrimaryKey(MessageEntity record);
}