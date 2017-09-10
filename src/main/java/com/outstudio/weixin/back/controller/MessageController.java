package com.outstudio.weixin.back.controller;

import com.github.pagehelper.PageHelper;
import com.outstudio.weixin.common.consts.ResponseStatus;
import com.outstudio.weixin.common.po.MessageEntity;
import com.outstudio.weixin.common.service.MessageService;
import com.outstudio.weixin.common.vo.MessageVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
        MessageVo messageVo = new MessageVo();
        int changedNum = messageService.addMessage(messageEntity);
        if (changedNum != 0) {
            messageVo.setStatus(ResponseStatus.CREATED)
                    .setMessage("created")
                    .setRedirectUrl(REDIRECT_URL);
        } else {
            messageVo.setStatus(ResponseStatus.DATABASE_ERROR)
                    .setMessage("database error")
                    .setRedirectUrl(REDIRECT_URL);
        }
        return messageVo;
    }

    @DeleteMapping("/messages/{id}")
    public MessageVo deleteMessage(@PathVariable Integer id) {
        MessageVo messageVo = new MessageVo();
        int changedNum = messageService.deleteMessage(id);
        if (changedNum != 0) {
            messageVo.setStatus(ResponseStatus.NO_CONTENT)
                    .setMessage("no content")
                    .setRedirectUrl(REDIRECT_URL);
        } else {
            messageVo.setStatus(ResponseStatus.RESOURCE_NOT_FOUND)
                    .setMessage("resource not found")
                    .setRedirectUrl(REDIRECT_URL);
        }
        return messageVo;
    }

    @PutMapping("/messages/{id}")
    public MessageVo modifyMessage(@PathVariable Integer id,
                                   @ModelAttribute MessageEntity messageEntity) {
        MessageVo messageVo = new MessageVo();
        messageEntity.setId(id);
        int changedNum = messageService.modifyMessage(messageEntity);
        if (changedNum != 0) {
            messageVo.setStatus(ResponseStatus.CREATED)
                    .setMessage("created")
                    .setRedirectUrl(REDIRECT_URL);
        } else {
            messageVo.setStatus(ResponseStatus.DATABASE_ERROR)
                    .setMessage("database error")
                    .setRedirectUrl(REDIRECT_URL);
        }
        return messageVo;
    }

    @GetMapping("/messages/{id}")
    public MessageVo getMessageById(@PathVariable Integer id) {
        MessageVo messageVo = new MessageVo();
        MessageEntity messageEntity = messageService.getMessageById(id);
        messageVo.setStatus(ResponseStatus.SUCCESS)
                .setMessage("success")
                .setRedirectUrl(REDIRECT_URL)
                .setData(messageEntity);
        return messageVo;
    }

    @GetMapping("/messages/page/{pageNum}")
    public MessageVo getAllMessages(@PathVariable Integer pageNum) {
        MessageVo messageVo = new MessageVo();
        PageHelper.startPage(pageNum, 15);
        List<MessageEntity> messageEntities = messageService.getAllMessages();
        messageVo.setStatus(ResponseStatus.SUCCESS)
                .setMessage("success")
                .setRedirectUrl(REDIRECT_URL)
                .setData(messageEntities);
        return messageVo;
    }
}