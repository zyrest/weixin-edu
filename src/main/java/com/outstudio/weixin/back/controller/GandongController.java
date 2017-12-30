package com.outstudio.weixin.back.controller;

import com.outstudio.weixin.back.exception.InvalidFileTypeException;
import com.outstudio.weixin.back.exception.SystemErrorException;
import com.outstudio.weixin.common.po.GandongEntity;
import com.outstudio.weixin.common.po.StoryEntity;
import com.outstudio.weixin.common.service.GandongService;
import com.outstudio.weixin.common.utils.FileUtil;
import com.outstudio.weixin.common.utils.MessageVoUtil;
import com.outstudio.weixin.common.vo.MessageVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by ZhouYing.
 * www.zhouying.xyz
 */
@Controller
@RequestMapping("/back/gandong")
public class GandongController {

    @Resource
    private GandongService gandongService;

    @PostMapping
    public MessageVo upload(@RequestParam("audio") MultipartFile audio,
                            HttpServletRequest request,
                            @ModelAttribute GandongEntity gandongEntity) {

        String fileType = FileUtil.getType(audio);
        if (!"mp3".equalsIgnoreCase(fileType))
            throw new InvalidFileTypeException("上传的音频格式应为MP3");

        String audioSrc;
        try {
            if (gandongEntity.getIs_free().equals(1))
                audioSrc = FileUtil.saveUploadFileAsUrlPath(request, audio, "free");
            else
                audioSrc = FileUtil.saveUploadFileAsUrlPath(request, audio, "charge");
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemErrorException();
        }

        gandongEntity.setSrc(audioSrc);

        gandongService.add(gandongEntity);
        return MessageVoUtil.created("");
    }

    @PutMapping("/{id}")
    public MessageVo modifyStory(@PathVariable("id") Integer id,
                                 @ModelAttribute GandongEntity gandongEntity) {
        gandongEntity.setId(id);
        int changedNum = gandongService.modify(gandongEntity);
        if (changedNum == 1) {
            return MessageVoUtil.created("", gandongService.getById(id));
        } else {
            return MessageVoUtil.resourceNotFound("");
        }
    }

    @DeleteMapping("/{id}")
    public MessageVo deleteStory(@PathVariable Integer id) {
        int changedNum = gandongService.deleteById(id);
        if (changedNum == 1) {
            return MessageVoUtil.noContent(null);
        } else {
            return MessageVoUtil.resourceNotFound(null);
        }
    }
}
