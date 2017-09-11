package com.outstudio.weixin.common.dao;

import com.outstudio.weixin.common.po.MessageEntity;

import java.util.List;

public interface MessageEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MessageEntity record);

    int insertSelective(MessageEntity record);

    MessageEntity selectByPrimaryKey(Integer id);

    List<MessageEntity> selectAll();

    List<MessageEntity> selectBysearchParam(String searchParam);

    int updateByPrimaryKeySelective(MessageEntity record);

    int updateByPrimaryKeyWithBLOBs(MessageEntity record);

    int updateByPrimaryKey(MessageEntity record);
}