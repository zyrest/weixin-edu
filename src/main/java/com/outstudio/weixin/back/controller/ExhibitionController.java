package com.outstudio.weixin.back.controller;

import com.outstudio.weixin.back.exception.InvalidFileTypeException;
import com.outstudio.weixin.back.exception.SystemErrorException;
import com.outstudio.weixin.common.po.ExhibitionEntity;
import com.outstudio.weixin.common.service.ExhibitionService;
import com.outstudio.weixin.common.utils.FileUtil;
import com.outstudio.weixin.common.utils.MessageVoUtil;
import com.outstudio.weixin.common.vo.ExhibitionType;
import com.outstudio.weixin.common.vo.MessageVo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by lmy on 2018/2/15.
 */
@RestController
@RequestMapping("/back/exhibition")
public class ExhibitionController {

    private final String REDIRECT_URL = "";

    @Resource
    private ExhibitionService exhibitionService;

    @PostMapping("/audios")
    public MessageVo backUploadAudio(@RequestParam("audio") MultipartFile audio,
                                     HttpServletRequest request,
                                     @ModelAttribute ExhibitionEntity exhibitionEntity) {

        String fileType = FileUtil.getType(audio);
        if (!"mp3".equalsIgnoreCase(fileType))
            throw new InvalidFileTypeException("上传的音频格式应为MP3");

        String audioSrc;
        try {
            audioSrc = FileUtil.saveUploadFileAsUrlPath(request, audio, "exhibition", "");
        } catch (IOException e) {
            e.printStackTrace();
            throw new SystemErrorException();
        }

        exhibitionEntity.setSrc(audioSrc);

        exhibitionService.backAdd(exhibitionEntity, ExhibitionType.AUDIO.getType());
        return MessageVoUtil.created(REDIRECT_URL);
    }

    @PostMapping("/videos")
    public MessageVo postVideo(@ModelAttribute ExhibitionEntity exhibitionEntity) {

        exhibitionService.backAdd(exhibitionEntity, ExhibitionType.VIDEO.getType());
        return MessageVoUtil.created(REDIRECT_URL);
    }

    @PostMapping("/picture")
    public MessageVo postPicture(@RequestParam("picture") MultipartFile picture,
                                 HttpServletRequest request,
                                 @ModelAttribute ExhibitionEntity exhibitionEntity) {
        String fileType = FileUtil.getType(picture);
        if (!"jpeg".equalsIgnoreCase(fileType) || !"jpg".equalsIgnoreCase(fileType) ||
                !"png".equalsIgnoreCase(fileType) || !"gif".equalsIgnoreCase(fileType) ||
                !"tiff".equalsIgnoreCase(fileType) || !"tif".equalsIgnoreCase(fileType))
            throw new InvalidFileTypeException("上传的图片格式不正确");

        String pictureSrc;
        try {
            pictureSrc = FileUtil.saveUploadFileAsUrlPath(request, picture, "exhibition", "");
        } catch (IOException e) {
            e.printStackTrace();
            throw new SystemErrorException();
        }

        exhibitionEntity.setSrc(pictureSrc);

        exhibitionService.backAdd(exhibitionEntity, ExhibitionType.PICTURE.getType());
        return MessageVoUtil.created(REDIRECT_URL);
    }


    @DeleteMapping("/{id}")
    public MessageVo deleteAudio(@PathVariable Integer id) {
        int changedNum = exhibitionService.deleteById(id);
        if (changedNum == 1) {
            return MessageVoUtil.noContent(REDIRECT_URL);
        } else {
            return MessageVoUtil.resourceNotFound(REDIRECT_URL);
        }
    }

}
