package com.outstudio.weixin.back.controller;

import com.outstudio.weixin.back.exception.InvalidFileTypeException;
import com.outstudio.weixin.back.exception.SystemErrorException;
import com.outstudio.weixin.common.po.EnVideoEntity;
import com.outstudio.weixin.common.service.EnVideoService;
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
public class EnVideoController {

    private static final String REDIRECT_URL = "";

    @Resource
    private EnVideoService enVideoService;

    @PostMapping("/enVideos")
    public MessageVo postEnVideo(@ModelAttribute EnVideoEntity enVideoEntity,
                                 @RequestParam("video") MultipartFile video,
                                 HttpServletRequest request) {

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
        return MessageVoUtil.created(REDIRECT_URL);
    }

    @DeleteMapping("/enVideos/{id}")
    public MessageVo deleteEnVideo(@PathVariable Integer id) {
        int changedNum = enVideoService.deleteEnVideo(id);
        if (changedNum != 0) {
            return MessageVoUtil.noContent(REDIRECT_URL);
        } else {
            return MessageVoUtil.resourceNotFound(REDIRECT_URL);
        }
    }

    @PatchMapping("/enVideos/{id}")
    public MessageVo modifyEnVideo(@PathVariable Integer id,
                                   @ModelAttribute EnVideoEntity enVideoEntity) {
        enVideoEntity.setId(id);
        int changedNum = enVideoService.modifyEnVideo(enVideoEntity);
        if (changedNum != 0) {
            return MessageVoUtil.created(REDIRECT_URL, enVideoService.getEnVideoById(id));
        } else {
            return MessageVoUtil.resourceNotFound(REDIRECT_URL);
        }
    }

}
