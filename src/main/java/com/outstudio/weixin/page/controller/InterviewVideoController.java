package com.outstudio.weixin.page.controller;

import com.github.pagehelper.PageHelper;
import com.outstudio.weixin.common.consts.ResponseStatus;
import com.outstudio.weixin.common.po.InterviewVideoEntity;
import com.outstudio.weixin.common.service.InterviewVideoService;
import com.outstudio.weixin.common.utils.MessageVoUtil;
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
        List<InterviewVideoEntity> interviewVideoEntities = interviewVideoService.getBySearchParam(searchParam);
        return MessageVoUtil.success(REDIRECT_URL, interviewVideoEntities);
    }

    @GetMapping("/interviewVideo/{id}")
    public MessageVo getenVideoById(@PathVariable Integer id) {
        InterviewVideoEntity interviewEntity = interviewVideoService.getInterviewVideoById(id);
        return MessageVoUtil.success(REDIRECT_URL, interviewEntity);
    }

    @GetMapping("/interviewVideos/page/{pageNum}")
    public MessageVo getAllInterviews(@PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, 15);
        List<InterviewVideoEntity> interviewVideoEntities = interviewVideoService.getAllInterviewVideos();
        return MessageVoUtil.success(REDIRECT_URL, interviewVideoEntities);
    }

    @GetMapping("/interviewVideos/stage/{stage}/page/{pageNum}")
    public MessageVo getInterviewsByStage(@PathVariable Integer stage,
                                          @PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, 15);
        List<InterviewVideoEntity> interviewVideoEntities = interviewVideoService.getInterviewVideosByStage(stage);
        return MessageVoUtil.success(REDIRECT_URL, interviewVideoEntities);
    }
}
