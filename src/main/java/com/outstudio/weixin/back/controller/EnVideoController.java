package com.outstudio.weixin.back.controller;

import com.github.pagehelper.PageHelper;
import com.outstudio.weixin.back.exception.InvalidFileTypeException;
import com.outstudio.weixin.back.exception.SystemErrorException;
import com.outstudio.weixin.common.consts.ResponseStatus;
import com.outstudio.weixin.common.po.EnVideoEntity;
import com.outstudio.weixin.common.service.EnVideoService;
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
public class EnVideoController {

    private static final String REDIRECT_URL = "";

    @Resource
    private EnVideoService enVideoService;

    @PostMapping("/enVideos")
    public MessageVo postEnVideo(@ModelAttribute EnVideoEntity enVideoEntity,
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

        enVideoEntity.setSrc(videoSrc);
        enVideoService.addEnVideo(enVideoEntity);
        messageVo.setStatus(ResponseStatus.CREATED)
                .setMessage("created")
                .setRedirectUrl(REDIRECT_URL);
        return messageVo;

    }

    @DeleteMapping("/enVideos/{id}")
    public MessageVo deleteEnVideo(@PathVariable Integer id) {
        MessageVo messageVo = new MessageVo();
        int changedNum = enVideoService.deleteEnVideo(id);
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

    @PatchMapping("/enVideos/{id}")
    public MessageVo modifyEnVideo(@PathVariable Integer id,
                                   @ModelAttribute EnVideoEntity enVideoEntity) {
        MessageVo messageVo = new MessageVo();
        enVideoEntity.setId(id);
        int changedNum = enVideoService.modifyEnVideo(enVideoEntity);
        if (changedNum != 0) {
            messageVo.setStatus(ResponseStatus.CREATED)
                    .setMessage("created")
                    .setRedirectUrl(REDIRECT_URL)
                    .setData(enVideoService.getEnVideoById(id));
        } else {
            messageVo.setStatus(ResponseStatus.RESOURCE_NOT_FOUND)
                    .setMessage("resource not found")
                    .setRedirectUrl(REDIRECT_URL);
        }
        return messageVo;
    }

    @GetMapping("/enVideos/{id}")
    public MessageVo getenVideoById(@PathVariable Integer id) {
        MessageVo messageVo = new MessageVo();
        EnVideoEntity enVideoEntity = enVideoService.getEnVideoById(id);
        messageVo.setStatus(ResponseStatus.SUCCESS)
                .setMessage("success")
                .setRedirectUrl(REDIRECT_URL)
                .setData(enVideoEntity);
        return messageVo;
    }

    @GetMapping("/enVideos/page/{pageNum}")
    public MessageVo getAllEnVideos(@PathVariable Integer pageNum) {
        MessageVo messageVo = new MessageVo();
        PageHelper.startPage(pageNum, 15);
        List<EnVideoEntity> enVideoEntities = enVideoService.getAllEnVideos();
        messageVo.setStatus(ResponseStatus.SUCCESS)
                .setMessage("success")
                .setRedirectUrl(REDIRECT_URL)
                .setData(enVideoEntities);
        return messageVo;
    }

    @GetMapping("/enVideos/stage/{stage}/page/{pageNum}")
    public MessageVo getEnVideosByStage(@PathVariable Integer stage,
                                        @PathVariable Integer pageNum) {
        MessageVo messageVo = new MessageVo();
        PageHelper.startPage(pageNum, 15);
        List<EnVideoEntity> enVideoEntities = enVideoService.getEnVideosByStage(stage);
        messageVo.setStatus(ResponseStatus.SUCCESS)
                .setMessage("success")
                .setRedirectUrl(REDIRECT_URL)
                .setData(enVideoEntities);
        return messageVo;
    }
}
