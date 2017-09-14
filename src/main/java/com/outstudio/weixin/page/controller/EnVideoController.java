package com.outstudio.weixin.page.controller;

import com.github.pagehelper.PageHelper;
import com.outstudio.weixin.common.consts.ResponseStatus;
import com.outstudio.weixin.common.po.EnVideoEntity;
import com.outstudio.weixin.common.service.EnVideoService;
import com.outstudio.weixin.common.utils.MessageVoUtil;
import com.outstudio.weixin.common.vo.MessageVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lmy on 2017/9/11.
 */
@RestController("enVideoPageController")
@RequestMapping("/open/page")
public class EnVideoController {

    private static final String REDIRECT_URL = "";

    @Resource
    private EnVideoService enVideoService;

    @GetMapping("/enVideos/{id}")
    public MessageVo getenVideoById(@PathVariable Integer id) {
        EnVideoEntity enVideoEntity = enVideoService.getEnVideoById(id);
        return MessageVoUtil.success(REDIRECT_URL, enVideoEntity);
    }

    @GetMapping("/enVideos/page/{pageNum}")
    public MessageVo getAllEnVideos(@PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, 15);
        List<EnVideoEntity> enVideoEntities = enVideoService.getAllEnVideos();
        return MessageVoUtil.success(REDIRECT_URL, enVideoEntities);
}

    @GetMapping("/enVideos/stage/{stage}/page/{pageNum}")
    public MessageVo getEnVideosByStage(@PathVariable Integer stage,
                                        @PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, 15);
        List<EnVideoEntity> enVideoEntities = enVideoService.getEnVideosByStage(stage);
        return MessageVoUtil.success(REDIRECT_URL, enVideoEntities);
    }

    @GetMapping("/enVideos")
    public MessageVo searchEnVideos(@RequestParam("searchParam") String searchParam) {
        List<EnVideoEntity> enVideoEntities = enVideoService.getEnVideosBySearchParam(searchParam);
        return MessageVoUtil.success(REDIRECT_URL, enVideoEntities);
    }
}
