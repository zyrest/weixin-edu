package com.outstudio.weixin.back.controller;

import com.outstudio.weixin.common.po.MessageEntity;
import com.outstudio.weixin.common.service.MessageService;
import com.outstudio.weixin.common.utils.MessageVoUtil;
import com.outstudio.weixin.common.vo.MessageVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by lmy on 2017/9/9.
 */
@RestController
@RequestMapping("/open/back")
public class MessageController {

    private static final String REDIRECT_URL = "";
    @Resource
    private MessageService messageService;

    @PostMapping("/messages")
    public MessageVo postMessage(@ModelAttribute MessageEntity messageEntity) {
        int changedNum = messageService.addMessage(messageEntity);
        if (changedNum != 0) {
            return MessageVoUtil.created(REDIRECT_URL);
        } else {
            return MessageVoUtil.databaseError(REDIRECT_URL);
        }
    }

    @DeleteMapping("/messages/{id}")
    public MessageVo deleteMessage(@PathVariable Integer id) {
        int changedNum = messageService.deleteMessage(id);
        if (changedNum != 0) {
            return MessageVoUtil.noContent(REDIRECT_URL);
        } else {
            return MessageVoUtil.resourceNotFound(REDIRECT_URL);
        }
    }

    @PutMapping("/messages/{id}")
    public MessageVo modifyMessage(@PathVariable Integer id,
                                   @ModelAttribute MessageEntity messageEntity) {
        messageEntity.setId(id);
        int changedNum = messageService.modifyMessage(messageEntity);
        if (changedNum != 0) {
            return MessageVoUtil.created(REDIRECT_URL, messageService.getMessageById(id));
        } else {
            return MessageVoUtil.resourceNotFound(REDIRECT_URL);
        }
    }
}