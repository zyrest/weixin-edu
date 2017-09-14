package com.outstudio.weixin.back.controller;

import com.outstudio.weixin.common.po.EnReviewEntity;
import com.outstudio.weixin.common.service.EnReviewService;
import com.outstudio.weixin.common.utils.MessageVoUtil;
import com.outstudio.weixin.common.vo.MessageVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by lmy on 2017/9/9.
 */
@RestController
@RequestMapping("/open/back")
public class EnReviewController {

    private static final String REDIRECT_URL = "";
    @Resource
    private EnReviewService enReviewService;

    @PostMapping("/enReviews")
    public MessageVo addEnReview(@ModelAttribute EnReviewEntity enReviewEntity) {
        int changedNum = enReviewService.addEnReview(enReviewEntity);
        if (changedNum != 0) {
            return MessageVoUtil.created(REDIRECT_URL);
        } else {
            return MessageVoUtil.databaseError(REDIRECT_URL);
        }
    }

    @DeleteMapping("/enReviews/{id}")
    public MessageVo deleteEnReview(@PathVariable Integer id) {
        int changedNum = enReviewService.deleteEnReviewById(id);
        if (changedNum != 0) {
            return MessageVoUtil.noContent(REDIRECT_URL);
        } else {
            return MessageVoUtil.resourceNotFound(REDIRECT_URL);
        }
    }

    @PutMapping("/enReviews/{id}")
    public MessageVo modifyEnReview(@PathVariable Integer id,
                                    @ModelAttribute EnReviewEntity enReviewEntity) {
        enReviewEntity.setId(id);
        int changedNum = enReviewService.modifyEnReview(enReviewEntity);
        if (changedNum != 0) {
            return MessageVoUtil.created(REDIRECT_URL, enReviewService.getEnReviewById(id));
        } else {
            return MessageVoUtil.resourceNotFound(REDIRECT_URL);
        }
    }

    @PatchMapping("/enReviews/{id}")
    public MessageVo modifyEnReviewExceptContent(@PathVariable Integer id,
                                                 @ModelAttribute EnReviewEntity enReviewEntity) {
        enReviewEntity.setId(id);
        int changedNum = enReviewService.modifyReviewExceptContent(enReviewEntity);
        if (changedNum != 0) {
            return MessageVoUtil.created(REDIRECT_URL, enReviewService.getEnReviewById(id));
        } else {
            return MessageVoUtil.resourceNotFound(REDIRECT_URL);
        }
    }

}
