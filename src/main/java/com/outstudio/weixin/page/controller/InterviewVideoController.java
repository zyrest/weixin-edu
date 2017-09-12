package com.outstudio.weixin.page.controller;

import com.github.pagehelper.PageHelper;
import com.outstudio.weixin.common.consts.ResponseStatus;
import com.outstudio.weixin.common.po.InterviewVideoEntity;
import com.outstudio.weixin.common.service.InterviewVideoService;
import com.outstudio.weixin.common.vo.MessageVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lmy on 2017/9/11.
 */
@RestController("interviewVideoPageController")
@RequestMapping("/open/page")
public class InterviewVideoController {
    private static final String REDIRECT_URL = "";

    @Resource
    private InterviewVideoService interviewVideoService;

    @GetMapping("/interviewVideos")
    public MessageVo searchInterviewVideos(@RequestParam("searchParam") String searchParam) {
        MessageVo messageVo = new MessageVo();
        List<InterviewVideoEntity> interviewVideoEntities = interviewVideoService.getBySearchParam(searchParam);
        messageVo.setStatus(ResponseStatus.SUCCESS)
                .setMessage("success")
                .setRedirectUrl(REDIRECT_URL)
                .setData(interviewVideoEntities);
        return messageVo;
    }

    @GetMapping("/interviewVideo/{id}")
    public MessageVo getenVideoById(@PathVariable Integer id) {
        MessageVo messageVo = new MessageVo();
        InterviewVideoEntity interviewEntity = interviewVideoService.getInterviewVideoById(id);
        messageVo.setStatus(com.outstudio.weixin.common.consts.ResponseStatus.SUCCESS)
                .setMessage("success")
                .setRedirectUrl(REDIRECT_URL)
                .setData(interviewEntity);
        return messageVo;
    }

    @GetMapping("/interviewVideos/page/{pageNum}")
    public MessageVo getAllInterviews(@PathVariable Integer pageNum) {
        MessageVo messageVo = new MessageVo();
        PageHelper.startPage(pageNum, 15);
        List<InterviewVideoEntity> interviewVideoEntities = interviewVideoService.getAllInterviewVideos();
        messageVo.setStatus(com.outstudio.weixin.common.consts.ResponseStatus.SUCCESS)
                .setMessage("success")
                .setRedirectUrl(REDIRECT_URL)
                .setData(interviewVideoEntities);
        return messageVo;
    }

    @GetMapping("/interviewVideos/stage/{stage}/page/{pageNum}")
    public MessageVo getInterviewsByStage(@PathVariable Integer stage,
                                          @PathVariable Integer pageNum) {
        MessageVo messageVo = new MessageVo();
        PageHelper.startPage(pageNum, 15);
        List<InterviewVideoEntity> interviewVideoEntities = interviewVideoService.getInterviewVideosByStage(stage);
        messageVo.setStatus(com.outstudio.weixin.common.consts.ResponseStatus.SUCCESS)
                .setMessage("success")
                .setRedirectUrl(REDIRECT_URL)
                .setData(interviewVideoEntities);
        return messageVo;
    }
}
