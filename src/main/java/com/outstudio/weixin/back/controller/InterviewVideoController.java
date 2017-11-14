package com.outstudio.weixin.back.controller;

import com.outstudio.weixin.back.exception.InvalidFileTypeException;
import com.outstudio.weixin.back.exception.SystemErrorException;
import com.outstudio.weixin.common.po.InterviewVideoEntity;
import com.outstudio.weixin.common.service.InterviewVideoService;
import com.outstudio.weixin.common.utils.FileUtil;
import com.outstudio.weixin.common.utils.MessageVoUtil;
import com.outstudio.weixin.common.vo.MessageVo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by lmy on 2017/9/9.
 */
@RestController
@RequestMapping("/back")
public class InterviewVideoController {

    private static final String REDIRECT_URL = "";
    @Resource
    private InterviewVideoService interviewVideoService;

    @PostMapping("/interviewVideos")
    public MessageVo postInterviewVideo(@ModelAttribute InterviewVideoEntity interviewVideoEntity) {

        interviewVideoService.addInterviewVideo(interviewVideoEntity);
        return MessageVoUtil.created(REDIRECT_URL);
    }

    @DeleteMapping("/interviewVideos/{id}")
    public MessageVo deleteInterviewVideo(@PathVariable Integer id) {
        int changedNum = interviewVideoService.deleteInterviewVideo(id);
        if (changedNum != 0) {
            return MessageVoUtil.noContent(REDIRECT_URL);
        } else {
            return MessageVoUtil.resourceNotFound(REDIRECT_URL);
        }
    }

    @PutMapping("/interviewVideo")
    public MessageVo modifyInterview(@PathVariable Integer id,
                                     @ModelAttribute InterviewVideoEntity interviewVideoEntity) {
        interviewVideoEntity.setId(id);
        int changedNum = interviewVideoService.modifyInterviewVideo(interviewVideoEntity);
        if (changedNum != 0) {
            return MessageVoUtil.created(REDIRECT_URL, interviewVideoService.getInterviewVideoById(id));
        } else {
            return MessageVoUtil.resourceNotFound(REDIRECT_URL);
        }
    }
}
