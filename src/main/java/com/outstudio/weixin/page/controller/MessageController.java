package com.outstudio.weixin.page.controller;

import com.github.pagehelper.PageHelper;
import com.outstudio.weixin.common.po.MessageEntity;
import com.outstudio.weixin.common.service.MessageService;
import com.outstudio.weixin.common.utils.MessageVoUtil;
import com.outstudio.weixin.common.vo.MessageVo;
import com.outstudio.weixin.core.shiro.token.TokenManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lmy on 2017/9/11.
 */
@Controller("messagePageController")
@RequestMapping("/page")
public class MessageController {
    private static final String REDIRECT_URL = "";

    private static final int pageSize = 15;

    @Resource
    private MessageService messageService;

    @GetMapping("/messages")
    @ResponseBody
    public MessageVo searchParam(@RequestParam("searchParam") String searchParam) {
        List<MessageEntity> messageEntities = messageService.getMessagesBySearchParam(searchParam);
        return MessageVoUtil.success(REDIRECT_URL, messageEntities);
    }

    @GetMapping("/messages/{id}")
    public ModelAndView getMessageById(@PathVariable Integer id) {
        ModelAndView view = new ModelAndView();
        MessageEntity messageEntity = messageService.getMessageById(id);
        view.setViewName("hide/page/messageOne");
        view.addObject("data", messageEntity);
        view.addObject("isVip", TokenManager.isVip("english"));
        return view;
    }

    @GetMapping("/messages/page/{pageNum}")
    @ResponseBody
    public MessageVo getAllMessages(@PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<MessageEntity> messageEntities = messageService.getAllMessages();
        return MessageVoUtil.success(REDIRECT_URL, messageEntities);
    }
}
