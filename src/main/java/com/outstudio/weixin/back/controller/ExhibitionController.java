package com.outstudio.weixin.back.controller;

import com.github.pagehelper.PageHelper;
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
import java.util.List;

/**
 * Created by lmy on 2018/2/15.
 */
@RestController
@RequestMapping("/back/exhibition")
public class ExhibitionController {

    private final String REDIRECT_URL = "";
    private final int pageSize = 15;

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

        exhibitionService.add(exhibitionEntity, ExhibitionType.AUDIO.getType(), 1);
        return MessageVoUtil.created(REDIRECT_URL);
    }

    @PostMapping("/videos")
    public MessageVo postVideo(@ModelAttribute ExhibitionEntity exhibitionEntity) {

        exhibitionService.add(exhibitionEntity, ExhibitionType.VIDEO.getType(), 1);
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

        exhibitionService.add(exhibitionEntity, ExhibitionType.PICTURE.getType(), 1);
        return MessageVoUtil.created(REDIRECT_URL);
    }

    @GetMapping("/notVerified/page/{pageNum}")
    public MessageVo getAllNotVerified(@PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<ExhibitionEntity> exhibitionEntities = exhibitionService.getByVerified(0);
        return MessageVoUtil.success(exhibitionEntities);
    }

    @GetMapping("/notVerified/{id}")
    public MessageVo getNotVerifiedById(@PathVariable Integer id) {
        return MessageVoUtil.success(exhibitionService.getById(id, 0));
    }

    @GetMapping("/notVerified/type/{type}/page/{pageNum}")
    public MessageVo getAllNotVerifiedByType(@PathVariable String type, @PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<ExhibitionEntity> exhibitionEntities = exhibitionService.getAllByType(type, 0);
        return MessageVoUtil.success(exhibitionEntities);
    }

    @GetMapping("/verify/{id}")
    public MessageVo verify(@PathVariable Integer id) {
        int result = exhibitionService.verify(id);
        if (result != 0) {
            return MessageVoUtil.success();
        } else {
            return MessageVoUtil.databaseError("");
        }
    }

    @DeleteMapping("/{id}")
    public MessageVo delete(@PathVariable Integer id) {
        int changedNum = exhibitionService.deleteById(id);
        if (changedNum == 1) {
            return MessageVoUtil.noContent(REDIRECT_URL);
        } else {
            return MessageVoUtil.resourceNotFound(REDIRECT_URL);
        }
    }

}
