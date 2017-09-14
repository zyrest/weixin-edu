package com.outstudio.weixin.back.controller;

import com.outstudio.weixin.back.exception.InvalidFileTypeException;
import com.outstudio.weixin.back.exception.SystemErrorException;
import com.outstudio.weixin.common.consts.ResponseStatus;
import com.outstudio.weixin.common.po.StoryEntity;
import com.outstudio.weixin.common.service.StoryService;
import com.outstudio.weixin.common.utils.FileUtil;
import com.outstudio.weixin.common.vo.MessageVo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * Created by lmy on 2017/9/9.
 */
@RestController
@RequestMapping("/back")
public class StoryController {

    private static final String REDIRECT_URL = "";

    @Resource
    private StoryService storyService;

    @PostMapping("/stories")
    public MessageVo uploadStory(@RequestParam("audio") MultipartFile audio,
                                 HttpServletRequest request,
                                 @ModelAttribute StoryEntity storyEntity) {
        MessageVo messageVo = new MessageVo();

        String fileType = FileUtil.getType(audio);
        if (!fileType.equals("mp3")||!fileType.equals("MP3"))
            throw new InvalidFileTypeException("上传的音频格式应为MP3");

        String audioSrc;
        try {
            audioSrc = FileUtil.saveUploadFile(request, audio);
        } catch (IOException e) {
            throw new SystemErrorException();
        }

        storyEntity.setSrc(audioSrc);
        storyEntity.setPost_date(new Date());
        storyService.addStory(storyEntity);

        messageVo.setStatus(ResponseStatus.CREATED)
                .setMessage("created")
                .setRedirectUrl(REDIRECT_URL);
        return messageVo;
    }

    @PatchMapping("/stories/{id}")
    public MessageVo modifyStory(@PathVariable("id") Integer id,
                                 @ModelAttribute StoryEntity storyEntity) {
        MessageVo messageVo = new MessageVo();
        storyEntity.setId(id);
        int changedNum = storyService.modifyStory(storyEntity);
        if (changedNum == 1) {
            messageVo.setStatus(ResponseStatus.CREATED)
                    .setMessage("created")
                    .setRedirectUrl(REDIRECT_URL)
                    .setData(storyService.getStoryById(id));
        } else {
            messageVo.setStatus(ResponseStatus.RESOURCE_NOT_FOUND)
                    .setMessage("resource not found")
                    .setRedirectUrl(REDIRECT_URL);
        }
        return messageVo;
    }

    @DeleteMapping("/stories/{id}")
    public MessageVo deleteStory(@PathVariable Integer id) {
        MessageVo messageVo = new MessageVo();
        int changedNum = storyService.deleteStoryById(id);
        if (changedNum == 1) {
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

//    @GetMapping("/stories/{id}")
//    public MessageVo getStory(@PathVariable Integer id) {
//        MessageVo messageVo = new MessageVo();
//        StoryEntity storyEntity = storyService.getStoryById(id);
//        messageVo.setStatus(ResponseStatus.SUCCESS)
//                .setMessage("success")
//                .setRedirectUrl(REDIRECT_URL)
//                .setData(storyEntity);
//        return messageVo;
//    }

//    @GetMapping("/stories/type/{type}/page/{pageNum}")
//    public MessageVo getStoriesByType(@PathVariable("type") Integer type,
//                                      @PathVariable("pageNum") Integer pageNum) {
//        MessageVo messageVo = new MessageVo();
//        PageHelper.startPage(pageNum, 15);
//        List<StoryEntity> storyEntities = storyService.getStoryByType(type);
//        messageVo.setStatus(ResponseStatus.SUCCESS)
//                .setMessage("success")
//                .setRedirectUrl(REDIRECT_URL)
//                .setData(storyEntities);
//        return messageVo;
//    }
//
//    @GetMapping("/stories/page/{pageNum}")
//    public MessageVo getAllStories(@PathVariable Integer pageNum) {
//        MessageVo messageVo = new MessageVo();
//        PageHelper.startPage(pageNum, 15);
//        List<StoryEntity> storyEntities = storyService.getAllStories();
//        messageVo.setStatus(ResponseStatus.SUCCESS)
//                .setMessage("success")
//                .setRedirectUrl(REDIRECT_URL)
//                .setData(storyEntities);
//        return messageVo;
//    }
}
