package com.outstudio.weixin.back.controller;

import com.outstudio.weixin.common.consts.ResponseStatus;
import com.outstudio.weixin.common.po.EnReviewEntity;
import com.outstudio.weixin.common.service.EnReviewService;
import com.outstudio.weixin.common.vo.MessageVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by lmy on 2017/9/9.
 */
@RestController
@RequestMapping("/back")
public class EnReviewController {

    private static final String REDIRECT_URL = "";
    @Resource
    private EnReviewService enReviewService;

    @PostMapping("/enReviews")
    public MessageVo addEnReview(@ModelAttribute EnReviewEntity enReviewEntity) {
        MessageVo messageVo = new MessageVo();
        int changedNum = enReviewService.addEnReview(enReviewEntity);
        if (changedNum != 0) {
            messageVo.setStatus(ResponseStatus.CREATED)
                    .setMessage("created")
                    .setRedirectUrl(REDIRECT_URL);
        } else {
            messageVo.setStatus(ResponseStatus.DATABASE_ERROR)
                    .setMessage("database error")
                    .setRedirectUrl(REDIRECT_URL);
        }
        return messageVo;
    }

    @DeleteMapping("/enReviews/{id}")
    public MessageVo deleteEnReview(@PathVariable Integer id) {
        MessageVo messageVo = new MessageVo();
        int changedNum = enReviewService.deleteEnReviewById(id);
        if (changedNum != 0) {
            messageVo.setStatus(ResponseStatus.NO_CONTENT)
                    .setMessage("no content")
                    .setRedirectUrl(REDIRECT_URL);
        } else {
            messageVo.setStatus(ResponseStatus.RESOURCE_NOT_FOUND)
                    .setMessage("resource not found")
                    .setRedirectUrl(REDIRECT_URL);
        }
        return messageVo;
    }

    @PutMapping("/enReviews/{id}")
    public MessageVo modifyEnReview(@PathVariable Integer id,
                                    @ModelAttribute EnReviewEntity enReviewEntity) {
        MessageVo messageVo = new MessageVo();
        enReviewEntity.setId(id);

        int changedNum = enReviewService.modifyEnReview(enReviewEntity);
        if (changedNum != 0) {
            messageVo.setStatus(ResponseStatus.CREATED)
                    .setMessage("created")
                    .setRedirectUrl(REDIRECT_URL);
        } else {
            messageVo.setStatus(ResponseStatus.RESOURCE_NOT_FOUND)
                    .setMessage("resource not found")
                    .setRedirectUrl(REDIRECT_URL);
        }
        return messageVo;
    }

    @PatchMapping("/enReviews/{id}")
    public MessageVo modifyEnReviewExceptContent(@PathVariable Integer id,
                                                 @ModelAttribute EnReviewEntity enReviewEntity) {
        MessageVo messageVo = new MessageVo();
        enReviewEntity.setId(id);
        int changedNum = enReviewService.modifyReviewExceptContent(enReviewEntity);
        if (changedNum != 0) {
            messageVo.setStatus(ResponseStatus.CREATED)
                    .setMessage("created")
                    .setRedirectUrl(REDIRECT_URL);
        } else {
            messageVo.setStatus(ResponseStatus.RESOURCE_NOT_FOUND)
                    .setMessage("resource not found")
                    .setRedirectUrl(REDIRECT_URL);
        }
        return messageVo;
    }

//    @GetMapping("/enReviews/{id}")
//    public MessageVo getEnReview(@PathVariable Integer id) {
//        MessageVo messageVo = new MessageVo();
//        EnReviewEntity enReviewEntity = enReviewService.getEnReviewById(id);
//        messageVo.setStatus(ResponseStatus.SUCCESS)
//                .setMessage("success")
//                .setRedirectUrl(REDIRECT_URL)
//                .setData(enReviewEntity);
//        return messageVo;
//    }
//
//    @GetMapping("/enReviews/page/{pageNum}")
//    public MessageVo getAllEnReviews(@PathVariable Integer pageNum) {
//        MessageVo messageVo = new MessageVo();
//        PageHelper.startPage(pageNum, 15);
//        List<EnReviewEntity> enReviewEntities = enReviewService.getAll();
//        messageVo.setStatus(ResponseStatus.SUCCESS)
//                .setMessage("success")
//                .setRedirectUrl(REDIRECT_URL)
//                .setData(enReviewEntities);
//        return messageVo;
//    }
//
//    @GetMapping("/enReviews/stage/{stage}/page/{pageNum}")
//    public MessageVo getEnReviewsByStage(@PathVariable Integer stage,
//                                         @PathVariable Integer pageNum) {
//        MessageVo messageVo = new MessageVo();
//        PageHelper.startPage(pageNum, 15);
//        List<EnReviewEntity> enReviewEntities = enReviewService.getByStage(stage);
//        messageVo.setStatus(ResponseStatus.SUCCESS)
//                .setMessage("success")
//                .setRedirectUrl(REDIRECT_URL)
//                .setData(enReviewEntities);
//        return messageVo;
//    }
}
