package com.outstudio.weixin.page.controller;

import com.github.pagehelper.PageHelper;
import com.outstudio.weixin.common.consts.ResponseStatus;
import com.outstudio.weixin.common.po.EnReviewEntity;
import com.outstudio.weixin.common.po.EnVideoEntity;
import com.outstudio.weixin.common.service.EnReviewService;
import com.outstudio.weixin.common.vo.MessageVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lmy on 2017/9/11.
 */
@RestController
@RequestMapping("/open/page")
public class EnReviewController {
    private static final String REDIRECT_URL = "";

    @Resource
    private EnReviewService enReviewService;

    @GetMapping("/enReviews/{id}")
    public MessageVo getEnReview(@PathVariable Integer id) {
        MessageVo messageVo = new MessageVo();
        EnReviewEntity enReviewEntity = enReviewService.getEnReviewById(id);
        messageVo.setStatus(ResponseStatus.SUCCESS)
                .setMessage("success")
                .setRedirectUrl(REDIRECT_URL)
                .setData(enReviewEntity);
        return messageVo;
    }

    @GetMapping("/enReviews/page/{pageNum}")
    public MessageVo getAllEnReviews(@PathVariable Integer pageNum) {
        MessageVo messageVo = new MessageVo();
        PageHelper.startPage(pageNum, 15);
        List<EnReviewEntity> enReviewEntities = enReviewService.getAll();
        messageVo.setStatus(ResponseStatus.SUCCESS)
                .setMessage("success")
                .setRedirectUrl(REDIRECT_URL)
                .setData(enReviewEntities);
        return messageVo;
    }

    @GetMapping("/enReviews/stage/{stage}/page/{pageNum}")
    public MessageVo getEnReviewsByStage(@PathVariable Integer stage,
                                         @PathVariable Integer pageNum) {
        MessageVo messageVo = new MessageVo();
        PageHelper.startPage(pageNum, 15);
        List<EnReviewEntity> enReviewEntities = enReviewService.getByStage(stage);
        messageVo.setStatus(ResponseStatus.SUCCESS)
                .setMessage("success")
                .setRedirectUrl(REDIRECT_URL)
                .setData(enReviewEntities);
        return messageVo;
    }

    @GetMapping("/enReviews")
    public MessageVo searchEnReviews(@RequestParam("searchParam") String searchParam) {
        MessageVo messageVo = new MessageVo();
        List<EnReviewEntity> enReviewEntities = enReviewService.getBySearchParam(searchParam);
        messageVo.setStatus(ResponseStatus.SUCCESS)
                .setMessage("success")
                .setRedirectUrl(REDIRECT_URL)
                .setData(enReviewEntities);
        return messageVo;
    }
}
