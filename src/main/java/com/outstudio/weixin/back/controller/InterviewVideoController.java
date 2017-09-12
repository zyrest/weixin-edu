package com.outstudio.weixin.back.controller;

import com.github.pagehelper.PageHelper;
import com.outstudio.weixin.back.exception.InvalidFileTypeException;
import com.outstudio.weixin.back.exception.SystemErrorException;
import com.outstudio.weixin.common.po.InterviewVideoEntity;
import com.outstudio.weixin.common.service.InterviewVideoService;
import com.outstudio.weixin.common.utils.FileUtil;
import com.outstudio.weixin.common.vo.MessageVo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by lmy on 2017/9/9.
 */
@RestController
@RequestMapping("/open/back")
public class InterviewVideoController {

    private static final String REDIRECT_URL = "";
    @Resource
    private InterviewVideoService interviewVideoService;

    @PostMapping("/interviewVideos")
    public MessageVo postInterviewVideo(@ModelAttribute InterviewVideoEntity interviewVideoEntity,
                                        @RequestParam("video") MultipartFile video,
                                        HttpServletRequest request) {
        MessageVo messageVo = new MessageVo();

        String fileType = FileUtil.getType(video);
        if (!fileType.equals("mp4") || !fileType.equals("MP4")) {
            throw new InvalidFileTypeException("上传的视频格式应为MP4");
        }

        String videoSrc;
        try {
            videoSrc = FileUtil.saveUploadFile(request, video);
        } catch (IOException e) {
            throw new SystemErrorException();
        }

        interviewVideoEntity.setSrc(videoSrc);
        interviewVideoService.addInterviewVideo(interviewVideoEntity);
        messageVo.setStatus(com.outstudio.weixin.common.consts.ResponseStatus.CREATED)
                .setMessage("created")
                .setRedirectUrl(REDIRECT_URL);
        return messageVo;
    }

    @DeleteMapping("/interviewVideos/{id}")
    public MessageVo deleteInterviewVideo(@PathVariable Integer id) {
        MessageVo messageVo = new MessageVo();
        int changedNum = interviewVideoService.deleteInterviewVideo(id);
        if (changedNum != 0) {
            messageVo.setStatus(com.outstudio.weixin.common.consts.ResponseStatus.NO_CONTENT)
                    .setMessage("no content")
                    .setRedirectUrl(REDIRECT_URL);
        } else {
            messageVo.setStatus(com.outstudio.weixin.common.consts.ResponseStatus.RESOURCE_NOT_FOUND)
                    .setMessage("resource not found")
                    .setRedirectUrl(REDIRECT_URL);
        }
        return messageVo;
    }

    @PutMapping("/interviewVideo")
    public MessageVo modifyInterview(@PathVariable Integer id,
                                     @ModelAttribute InterviewVideoEntity interviewVideoEntity) {
        MessageVo messageVo = new MessageVo();
        interviewVideoEntity.setId(id);
        int changedNum = interviewVideoService.modifyInterviewVideo(interviewVideoEntity);
        if (changedNum != 0) {
            messageVo.setStatus(com.outstudio.weixin.common.consts.ResponseStatus.CREATED)
                    .setMessage("created")
                    .setRedirectUrl(REDIRECT_URL)
                    .setData(interviewVideoService.getInterviewVideoById(id));
        } else {
            messageVo.setStatus(com.outstudio.weixin.common.consts.ResponseStatus.RESOURCE_NOT_FOUND)
                    .setMessage("resource not found")
                    .setRedirectUrl(REDIRECT_URL);
        }
        return messageVo;
    }

//    @GetMapping("/interviewVideo/{id}")
//    public MessageVo getenVideoById(@PathVariable Integer id) {
//        MessageVo messageVo = new MessageVo();
//        InterviewVideoEntity interviewEntity = interviewVideoService.getInterviewVideoById(id);
//        messageVo.setStatus(com.outstudio.weixin.common.consts.ResponseStatus.SUCCESS)
//                .setMessage("success")
//                .setRedirectUrl(REDIRECT_URL)
//                .setData(interviewEntity);
//        return messageVo;
//    }
//
//    @GetMapping("/interviewVideos/page/{pageNum}")
//    public MessageVo getAllInterviews(@PathVariable Integer pageNum) {
//        MessageVo messageVo = new MessageVo();
//        PageHelper.startPage(pageNum, 15);
//        List<InterviewVideoEntity> interviewVideoEntities = interviewVideoService.getAllInterviewVideos();
//        messageVo.setStatus(com.outstudio.weixin.common.consts.ResponseStatus.SUCCESS)
//                .setMessage("success")
//                .setRedirectUrl(REDIRECT_URL)
//                .setData(interviewVideoEntities);
//        return messageVo;
//    }
//
//    @GetMapping("/interviewVideos/stage/{stage}/page/{pageNum}")
//    public MessageVo getInterviewsByStage(@PathVariable Integer stage,
//                                          @PathVariable Integer pageNum) {
//        MessageVo messageVo = new MessageVo();
//        PageHelper.startPage(pageNum, 15);
//        List<InterviewVideoEntity> interviewVideoEntities = interviewVideoService.getInterviewVideosByStage(stage);
//        messageVo.setStatus(com.outstudio.weixin.common.consts.ResponseStatus.SUCCESS)
//                .setMessage("success")
//                .setRedirectUrl(REDIRECT_URL)
//                .setData(interviewVideoEntities);
//        return messageVo;
//    }
}
