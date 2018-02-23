package com.outstudio.weixin.page.controller;

import com.github.pagehelper.PageHelper;
import com.outstudio.weixin.common.po.EnReviewEntity;
import com.outstudio.weixin.common.vo.VipType;
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
    private static final int pageSize = 10;

    @Resource
    private EnReviewService enReviewService;

    @GetMapping("/enReviews/{id}")
    public ModelAndView getEnReview(@PathVariable Integer id) {
        ModelAndView view = new ModelAndView();
        EnReviewEntity enReviewEntity = enReviewService.getEnReviewById(id);
        view.addObject("data", enReviewEntity);
        view.addObject("isVip", TokenManager.isVip(enReviewEntity.getType()));
        view.setViewName("hide/page/reviewOne");
        return view;
    }

    @GetMapping("/enReviews/page/{pageNum}/{type}")
    @ResponseBody
    public MessageVo getAllEnReviews(@PathVariable int pageNum,
                                     @PathVariable String type) {
        PageHelper.startPage(pageNum, pageSize);
        List<EnReviewEntity> enReviewEntities = enReviewService.getAll(type);
        return MessageVoUtil.success(REDIRECT_URL, enReviewEntities);
    }

    @GetMapping("/enReviews/stage/{stage}/page/{pageNum}/{type}")
    @ResponseBody
    public MessageVo getEnReviewsByStage(@PathVariable Integer stage,
                                         @PathVariable Integer pageNum,
                                         @PathVariable String type) {
        PageHelper.startPage(pageNum, pageSize);
        List<EnReviewEntity> enReviewEntities = enReviewService.getByStage(stage, type);
        return MessageVoUtil.success(REDIRECT_URL, enReviewEntities);
    }

    /**
     * 搜索接口
     *
     * @param searchParam 搜索的参数
     * @return
     */
    @GetMapping("/enReviews/{type}")
    @ResponseBody
    public MessageVo searchEnReviews(@RequestParam("searchParam") String searchParam,
                                     @PathVariable String type) {
        List<EnReviewEntity> enReviewEntities = enReviewService.getBySearchParam(searchParam, type);
        return MessageVoUtil.success(REDIRECT_URL, enReviewEntities);
    }

    @GetMapping("/enReviews/stages/{type}")
    @ResponseBody
    public MessageVo getStages(@PathVariable String type) {
        return MessageVoUtil.success(REDIRECT_URL, enReviewService.getStage(type));
    }

    @GetMapping("/enReviews/pageNum/{type}")
    @ResponseBody
    public MessageVo getNum(@PathVariable String type) {

        return MessageVoUtil.success(getTotalPageNum(type));
    }

    private Long getTotalPageNum(String type) {
        Long got = enReviewService.getCount(type);
        Long ans = got / pageSize;
        if (got % pageSize != 0) {
            ans += 1;
        }

        return ans;
    }
}
