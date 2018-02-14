package com.outstudio.weixin.page.controller;

import com.github.pagehelper.PageHelper;
import com.outstudio.weixin.common.po.InterviewVideoEntity;
import com.outstudio.weixin.common.service.InterviewVideoService;
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
@Controller("interviewVideoPageController")
@RequestMapping("/page")
public class InterviewVideoController {
    private static final String REDIRECT_URL = "";
    private static final int pageSize = 10;

    @Resource
    private InterviewVideoService interviewVideoService;

    @GetMapping("/interviewVideos")
    @ResponseBody
    public MessageVo searchInterviewVideos(@RequestParam("searchParam") String searchParam) {
        List<InterviewVideoEntity> interviewVideoEntities = interviewVideoService.getBySearchParam(searchParam);
        return MessageVoUtil.success(REDIRECT_URL, interviewVideoEntities);
    }

    @GetMapping("/interviewVideos/{id}")
    public ModelAndView getenVideoById(@PathVariable Integer id) {
        ModelAndView view = new ModelAndView();
        InterviewVideoEntity interviewEntity = interviewVideoService.getInterviewVideoById(id);
        view.addObject("data", interviewEntity);
        view.addObject("isVip", TokenManager.isVip("english"));
        view.setViewName("hide/page/videoOne");
        return view;
    }

    @GetMapping("/interviewVideos/page/{pageNum}")
    @ResponseBody
    public MessageVo getAllInterviews(@PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<InterviewVideoEntity> interviewVideoEntities = interviewVideoService.getAllInterviewVideos();
        return MessageVoUtil.success(REDIRECT_URL, interviewVideoEntities);
    }

    @GetMapping("/interviewVideos/stage/{stage}/page/{pageNum}")
    @ResponseBody
    public MessageVo getInterviewsByStage(@PathVariable Integer stage,
                                          @PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<InterviewVideoEntity> interviewVideoEntities = interviewVideoService.getInterviewVideosByStage(stage);
        return MessageVoUtil.success(REDIRECT_URL, interviewVideoEntities);
    }

    @GetMapping("/interviewVideos/stages")
    @ResponseBody
    public MessageVo getStages() {
        return MessageVoUtil.success(REDIRECT_URL, interviewVideoService.getStage());
    }

    @GetMapping("/interviewVideos/pageNum")
    @ResponseBody
    public MessageVo getPageNum() {
        Long got = interviewVideoService.getCount();
        Long ans = got / pageSize;
        if (got % pageSize != 0) {
            ans += 1;
        }
        return MessageVoUtil.success(ans);
    }
}
