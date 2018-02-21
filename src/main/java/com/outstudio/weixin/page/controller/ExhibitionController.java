package com.outstudio.weixin.page.controller;

import com.github.pagehelper.PageHelper;
import com.outstudio.weixin.back.exception.InvalidFileTypeException;
import com.outstudio.weixin.back.exception.SystemErrorException;
import com.outstudio.weixin.common.consts.ResponseStatus;
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
 * Created by lmy on 2018/2/19.
 */
@RestController
@RequestMapping("/page/exhibition")
public class ExhibitionController {

    private final int pageSize = 15;

    @Resource
    private ExhibitionService exhibitionService;

    @GetMapping("/page/{page}/{type}")
    public MessageVo getAll(@PathVariable String type,
                            @PathVariable Integer page) {
        PageHelper.startPage(page, pageSize);
        List<ExhibitionEntity> entities = exhibitionService.getAllByType(type, 1);
        return MessageVoUtil.success(entities);
    }

    @GetMapping("/{id}")
    public MessageVo getById(@PathVariable Integer id) {
        return MessageVoUtil.success(exhibitionService.getById(id, 1));
    }

    @GetMapping("/search")
    public MessageVo search(@RequestParam("searchParam") String searchParam) {
        List<ExhibitionEntity> entities = exhibitionService.getBySearchParam(searchParam, 1);
        return MessageVoUtil.success(entities);
    }

    @GetMapping("/pageNum/{type}")
    public MessageVo getTotalPage(@PathVariable String type) {
        return MessageVoUtil.success(exhibitionService.getCountByType(type));
    }

    @PutMapping("/upvote/{id}")
    public MessageVo upvote(@PathVariable Integer id,
                            HttpServletRequest request) {
        boolean result = exhibitionService.upvote(id, request.getRemoteAddr(), 1);
        if (result) {
            return MessageVoUtil.success();
        } else {
            return new MessageVo().setStatus(ResponseStatus.EXCESSIVE_ATTEMPT).setMessage("该用户已经投票");
        }
    }

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

        exhibitionService.add(exhibitionEntity, ExhibitionType.AUDIO.getType(), 0);
        return MessageVoUtil.created("");
    }

    @PostMapping("/videos")
    public MessageVo postVideo(@ModelAttribute ExhibitionEntity exhibitionEntity) {

        exhibitionService.add(exhibitionEntity, ExhibitionType.VIDEO.getType(), 0);
        return MessageVoUtil.created("");
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

        exhibitionService.add(exhibitionEntity, ExhibitionType.PICTURE.getType(), 0);
        return MessageVoUtil.created("");
    }

}