package com.outstudio.weixin.page.controller;

import com.github.pagehelper.PageHelper;
import com.outstudio.weixin.common.consts.ResponseStatus;
import com.outstudio.weixin.common.po.EnReviewEntity;
import com.outstudio.weixin.common.po.EnVideoEntity;
import com.outstudio.weixin.common.service.EnReviewService;
import com.outstudio.weixin.common.utils.MessageVoUtil;
import com.outstudio.weixin.common.vo.MessageVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lmy on 2017/9/11.
 */
@RestController("enReviewPageController")
@RequestMapping("/open/page")
public class EnReviewController {
    private static final String REDIRECT_URL = "";

    @Resource
    private EnReviewService enReviewService;

    @GetMapping("/enReviews/{id}")
    public MessageVo getEnReview(@PathVariable Integer id) {
        EnReviewEntity enReviewEntity = enReviewService.getEnReviewById(id);
        return MessageVoUtil.success(enReviewEntity);
    }

    @GetMapping("/enReviews/page/{pageNum}")
    public MessageVo getAllEnReviews(@PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, 15);
        List<EnReviewEntity> enReviewEntities = enReviewService.getAll();
        return MessageVoUtil.success(REDIRECT_URL, enReviewEntities);
    }

    @GetMapping("/enReviews/stage/{stage}/page/{pageNum}")
    public MessageVo getEnReviewsByStage(@PathVariable Integer stage,
                                         @PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, 15);
        List<EnReviewEntity> enReviewEntities = enReviewService.getByStage(stage);
        return MessageVoUtil.success(REDIRECT_URL, enReviewEntities);
    }

    /**
     * 搜索接口
     * @param searchParam 搜索的参数
     * @return
     */
    @GetMapping("/enReviews")
    public MessageVo searchEnReviews(@RequestParam("searchParam") String searchParam) {
        List<EnReviewEntity> enReviewEntities = enReviewService.getBySearchParam(searchParam);
        return MessageVoUtil.success(REDIRECT_URL, enReviewEntities);
    }
}
