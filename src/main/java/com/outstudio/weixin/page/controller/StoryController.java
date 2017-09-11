package com.outstudio.weixin.page.controller;

import com.github.pagehelper.PageHelper;
import com.outstudio.weixin.common.consts.ResponseStatus;
import com.outstudio.weixin.common.po.StoryEntity;
import com.outstudio.weixin.common.service.StoryService;
import com.outstudio.weixin.common.vo.MessageVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lmy on 2017/9/11.
 */
@RestController
@RequestMapping("/open/page")
public class StoryController {

    private static final String REDIRECT_URL = "";
    @Resource
    private StoryService service;

    @GetMapping("/stories/type/{type}/page/{pageNum}")
    public MessageVo getStotiesByType(@PathVariable Integer type,
                                      @PathVariable Integer pageNum) {
        MessageVo messageVo = new MessageVo();
        PageHelper.startPage(pageNum, 15);
        List<StoryEntity> storyEntities = service.getStoryByType(type);
        messageVo.setStatus(ResponseStatus.SUCCESS)
                .setMessage("success")
                .setRedirectUrl(REDIRECT_URL)
                .setData(storyEntities);
        return messageVo;
    }
}
