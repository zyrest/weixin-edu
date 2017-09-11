package com.outstudio.weixin.common.service;

import com.outstudio.weixin.common.dao.MessageEntityMapper;
import com.outstudio.weixin.common.po.MessageEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by lmy on 2017/9/10.
 */
@Service
public class MessageService {

    @Resource
    private MessageEntityMapper messageEntityMapper;

    public int addMessage(MessageEntity messageEntity) {
        messageEntity.setLast_modify_date(new Date());
        messageEntity.setPost_date(new Date());
        return messageEntityMapper.insert(messageEntity);
    }

    public int deleteMessage(Integer id) {
        return messageEntityMapper.deleteByPrimaryKey(id);
    }

    public int modifyMessage(MessageEntity messageEntity) {
        messageEntity.setLast_modify_date(new Date());
        return messageEntityMapper.updateByPrimaryKeySelective(messageEntity);
    }

    public MessageEntity getMessageById(Integer id) {
        return messageEntityMapper.selectByPrimaryKey(id);
    }

    public List<MessageEntity> getAllMessages() {
        return messageEntityMapper.selectAll();
    }

    public List<MessageEntity> getMessagesBySearchParam(String searchParam) {
        return messageEntityMapper.selectBysearchParam(searchParam);
    }
}
