package com.outstudio.weixin.page.controller;

import com.github.pagehelper.PageHelper;
import com.outstudio.weixin.common.consts.ResponseStatus;
import com.outstudio.weixin.common.po.MessageEntity;
import com.outstudio.weixin.common.service.MessageService;
import com.outstudio.weixin.common.utils.MessageVoUtil;
import com.outstudio.weixin.common.vo.MessageVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lmy on 2017/9/11.
 */
@RestController("messagePageController")
@RequestMapping("/open/page")
public class MessageController {
    private static final String REDIRECT_URL = "";

    @Resource
    private MessageService messageService;

    @GetMapping("/messages")
    public MessageVo searchParam(@RequestParam("searchParam") String searchParam) {
        List<MessageEntity> messageEntities = messageService.getMessagesBySearchParam(searchParam);
        return MessageVoUtil.success(REDIRECT_URL, messageEntities);
    }

    @GetMapping("/messages/{id}")
    public MessageVo getMessageById(@PathVariable Integer id) {
        MessageEntity messageEntity = messageService.getMessageById(id);
        return MessageVoUtil.success(REDIRECT_URL, messageEntity);
    }

    @GetMapping("/messages/page/{pageNum}")
    public MessageVo getAllMessages(@PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, 15);
        List<MessageEntity> messageEntities = messageService.getAllMessages();
        return MessageVoUtil.success(REDIRECT_URL, messageEntities);
    }
}
