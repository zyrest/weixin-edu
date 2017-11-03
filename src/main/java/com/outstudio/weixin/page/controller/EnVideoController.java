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

    @GetMapping("/enVideos/page/{pageNum}")
    @ResponseBody
    public MessageVo getAllEnVideos(@PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<EnVideoEntity> enVideoEntities = enVideoService.getAllEnVideos();
        return MessageVoUtil.success(REDIRECT_URL, enVideoEntities);
}

    @GetMapping("/enVideos/stage/{stage}/page/{pageNum}")
    @ResponseBody
    public MessageVo getEnVideosByStage(@PathVariable Integer stage,
                                        @PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<EnVideoEntity> enVideoEntities = enVideoService.getEnVideosByStage(stage);
        return MessageVoUtil.success(REDIRECT_URL, enVideoEntities);
    }

    @GetMapping("/enVideos")
    @ResponseBody
    public MessageVo searchEnVideos(@RequestParam("searchParam") String searchParam) {
        List<EnVideoEntity> enVideoEntities = enVideoService.getEnVideosBySearchParam(searchParam);
        return MessageVoUtil.success(REDIRECT_URL, enVideoEntities);
    }

    @GetMapping("/enVideos/stages")
    @ResponseBody
    public MessageVo getStages() {
        return MessageVoUtil.success(REDIRECT_URL, enVideoService.getStage());
    }

    @GetMapping("/enVideos/pageNum")
    @ResponseBody
    public MessageVo getNum() {

        return MessageVoUtil.success(getTotalPageNum());
    }

    private Long getTotalPageNum() {
        Long got = enVideoService.getCount();
        Long ans = got / pageSize;
        if (got % pageSize != 0) {
            ans += 1;
        }

        return ans;
    }
}
