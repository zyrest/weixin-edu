package com.outstudio.weixin.page.controller;

import com.github.pagehelper.PageHelper;
import com.outstudio.weixin.common.po.EnReviewEntity;
import com.outstudio.weixin.common.service.EnReviewService;
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
@Controller("enReviewPageController")
@RequestMapping("/page")
public class EnReviewController {
    private static final String REDIRECT_URL = "";
    private static final int pageSize = 15;

    @Resource
    private EnReviewService enReviewService;

    @GetMapping("/enReviews/{id}")
    public ModelAndView getEnReview(@PathVariable Integer id) {
        ModelAndView view = new ModelAndView();
        EnReviewEntity enReviewEntity = enReviewService.getEnReviewById(id);
        view.addObject("data", enReviewEntity);
        view.addObject("isVip", TokenManager.isVip());
        view.setViewName("hide/page/reviewOne");
        return view;
    }

    @GetMapping("/enReviews/page/{pageNum}")
    @ResponseBody
    public MessageVo getAllEnReviews(@PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<EnReviewEntity> enReviewEntities = enReviewService.getAll();
        return MessageVoUtil.success(REDIRECT_URL, enReviewEntities);
    }

    @GetMapping("/enReviews/stage/{stage}/page/{pageNum}")
    @ResponseBody
    public MessageVo getEnReviewsByStage(@PathVariable Integer stage,
                                         @PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<EnReviewEntity> enReviewEntities = enReviewService.getByStage(stage);
        return MessageVoUtil.success(REDIRECT_URL, enReviewEntities);
    }

    /**
     * 搜索接口
     * @param searchParam 搜索的参数
     * @return
     */
    @GetMapping("/enReviews")
    @ResponseBody
    public MessageVo searchEnReviews(@RequestParam("searchParam") String searchParam) {
        List<EnReviewEntity> enReviewEntities = enReviewService.getBySearchParam(searchParam);
        return MessageVoUtil.success(REDIRECT_URL, enReviewEntities);
    }

    @GetMapping("/enReviews/stages")
    @ResponseBody
    public MessageVo getStages() {
        return MessageVoUtil.success(REDIRECT_URL, enReviewService.getStage());
    }

}
