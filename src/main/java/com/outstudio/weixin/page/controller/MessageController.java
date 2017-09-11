package com.outstudio.weixin.page.controller;

import com.github.pagehelper.PageHelper;
import com.outstudio.weixin.common.consts.ResponseStatus;
import com.outstudio.weixin.common.po.MessageEntity;
import com.outstudio.weixin.common.service.MessageService;
import com.outstudio.weixin.common.vo.MessageVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lmy on 2017/9/11.
 */
@RestController
@RequestMapping("/open/page")
public class MessageController {
    private static final String REDIRECT_URL = "";

    @Resource
    private MessageService messageService;

    @GetMapping("/messages")
    public MessageVo searchParam(@RequestParam("searchParam") String searchParam) {
        MessageVo messageVo = new MessageVo();
        List<MessageEntity> messageEntities = messageService.getMessagesBySearchParam(searchParam);
        messageVo.setStatus(ResponseStatus.SUCCESS)
                .setMessage("success")
                .setRedirectUrl(REDIRECT_URL)
                .setData(messageEntities);
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
