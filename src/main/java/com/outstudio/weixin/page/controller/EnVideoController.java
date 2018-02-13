package com.outstudio.weixin.page.controller;

import com.github.pagehelper.PageHelper;
import com.outstudio.weixin.common.po.EnVideoEntity;
import com.outstudio.weixin.common.service.EnVideoService;
import com.outstudio.weixin.common.utils.MessageVoUtil;
import com.outstudio.weixin.common.vo.MessageVo;
import com.outstudio.weixin.core.shiro.token.TokenManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lmy on 2017/9/11.
 */
@Controller("enVideoPageController")
@RequestMapping("/page")
public class EnVideoController {

    private static final String REDIRECT_URL = "";
    private static final int pageSize = 15;

    @Resource
    private EnVideoService enVideoService;

    @GetMapping("/enVideos/{id}")
    public ModelAndView getenVideoById(@PathVariable Integer id) {
        ModelAndView view = new ModelAndView();
        EnVideoEntity enVideoEntity = enVideoService.getEnVideoById(id);
        view.addObject("data", enVideoEntity);
        view.addObject("isVip", TokenManager.isVip());
        view.setViewName("hide/page/enVideoOne");
        return view;
    }

    @GetMapping("/enVideos/page/{pageNum}/{type}")
    @ResponseBody
    public MessageVo getAllEnVideos(@PathVariable Integer pageNum,
                                    @PathVariable String type) {
        PageHelper.startPage(pageNum, pageSize);
        List<EnVideoEntity> enVideoEntities = enVideoService.getAllEnVideos(type);
        return MessageVoUtil.success(REDIRECT_URL, enVideoEntities);
    }

    @GetMapping("/enVideos/stage/{stage}/page/{pageNum}/{type}")
    @ResponseBody
    public MessageVo getEnVideosByStage(@PathVariable Integer stage,
                                        @PathVariable Integer pageNum,
                                        @PathVariable String type) {
        PageHelper.startPage(pageNum, pageSize);
        List<EnVideoEntity> enVideoEntities = enVideoService.getEnVideosByStage(stage, type);
        return MessageVoUtil.success(REDIRECT_URL, enVideoEntities);
    }

    @GetMapping("/enVideos/{type}")
    @ResponseBody
    public MessageVo searchEnVideos(@RequestParam("searchParam") String searchParam,
                                    @PathVariable String type) {
        List<EnVideoEntity> enVideoEntities = enVideoService.getEnVideosBySearchParam(searchParam, type);
        return MessageVoUtil.success(REDIRECT_URL, enVideoEntities);
    }

    @GetMapping("/enVideos/stages/{type}")
    @ResponseBody
    public MessageVo getStages(@PathVariable String type) {
        return MessageVoUtil.success(REDIRECT_URL, enVideoService.getStage(type));
    }

    @GetMapping("/enVideos/pageNum/{type}")
    @ResponseBody
    public MessageVo getNum(@PathVariable String type) {

        return MessageVoUtil.success(getTotalPageNum(type));
    }

    private Long getTotalPageNum(String type) {
        Long got = enVideoService.getCount(type);
        Long ans = got / pageSize;
        if (got % pageSize != 0) {
            ans += 1;
        }

        return ans;
    }
}
