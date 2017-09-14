package com.outstudio.weixin.back.controller;

import com.outstudio.weixin.common.po.WelcomeEntity;
import com.outstudio.weixin.common.service.WelcomeService;
import com.outstudio.weixin.common.utils.MessageVoUtil;
import com.outstudio.weixin.common.vo.MessageVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 前端界面初步设想：
 *
 * 如果管理员新增一条欢迎信息，默认使用此信息作为用户关注时推送的信息。对应 @PostMapping("/welcomes")
 * 可以获取发布过的所有信息。对应@GetMapping("/welcomes")
 * 可以获取发布过具体信息。对应@GetMapping("/welcomes/{id}") ---貌似没啥用
 * 在管理员创建的每条信息后面可以选择使用此条。对应 @PatchMapping("/welcomes/{id}")
 * 可以对发布过的历史信息进行删除。对应@DeleteMapping("/welcomes/{id}")
 * 当前使用中的信息不能删除
 * 可以对发布过的或者正在使用的信息进行修改。对应@PutMapping("/welcomes/{id}")
 */
@RestController
@RequestMapping("/open/back")
public class WelcomeController {

    @Resource
    private WelcomeService welcomeService;

    private static final String REDIRECT_URL = "";

    @GetMapping("/welcomes")
    public MessageVo getWelcomes() {
        return MessageVoUtil.success(welcomeService.getAll());
    }

    @GetMapping("/welcomes/{id}")
    public MessageVo getWelcomeById(@PathVariable("id") Integer id) {
        return MessageVoUtil.success(welcomeService.getById(id));
    }

    @PostMapping("/welcomes")
    public MessageVo post(@ModelAttribute WelcomeEntity welcomeEntity) {
        int changedNum = welcomeService.post(welcomeEntity);
        if (changedNum != 0) {
            return MessageVoUtil.created(REDIRECT_URL);
        } else {
            return MessageVoUtil.databaseError(REDIRECT_URL);
        }
    }

    @DeleteMapping("/welcomes/{id}")
    public MessageVo deleteWelcomeById(@PathVariable("id") Integer id) {
        int changedNum = welcomeService.deleteById(id);
        if (changedNum != 0) {
            return MessageVoUtil.noContent(REDIRECT_URL);
        } else {
            return MessageVoUtil.resourceIsUsing();
        }
    }

    @PutMapping("/welcomes/{id}")
    public MessageVo modifyWelcome(@PathVariable("id") Integer id,
                                   @ModelAttribute WelcomeEntity welcomeEntity) {
        welcomeEntity.setId(id);
        int changedNum = welcomeService.modifyById(welcomeEntity);
        if (changedNum != 0) {
            return MessageVoUtil.created(REDIRECT_URL, welcomeService.getById(id));
        } else {
            return MessageVoUtil.resourceNotFound(REDIRECT_URL);
        }

    }

    @PatchMapping("/welcomes/{id}")
    public MessageVo useWelcomeById(@PathVariable("id") Integer id) {
        int changedNum = welcomeService.useById(id);
        if (changedNum != 0) {
            return MessageVoUtil.created(REDIRECT_URL, welcomeService.getById(id));
        } else {
            return MessageVoUtil.resourceNotFound(REDIRECT_URL);
        }
    }


}
