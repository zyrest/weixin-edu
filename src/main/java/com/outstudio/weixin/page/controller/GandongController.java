package com.outstudio.weixin.page.controller;

import com.outstudio.weixin.common.po.GandongEntity;
import com.outstudio.weixin.common.vo.VipType;
import com.outstudio.weixin.common.service.GandongService;
import com.outstudio.weixin.common.utils.MessageVoUtil;
import com.outstudio.weixin.common.vo.MessageVo;
import com.outstudio.weixin.core.shiro.token.TokenManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ZhouYing.
 * www.zhouying.xyz
 */
@Controller("gandongPageController")
@RequestMapping("/page/gandong")
public class GandongController {

    private static final int pageSize = 10;

    private static final String REDIRECT_URL = "";
    @Resource
    private GandongService gandongService;

    @GetMapping("/page/{pageNum}")
    @ResponseBody
    public MessageVo getAllStories(@PathVariable Integer pageNum) {
        List<GandongEntity> gandongEntities = gandongService.getAllPageable(pageNum, pageSize);
        return MessageVoUtil.success(REDIRECT_URL, gandongEntities);
    }

    @GetMapping("/{id}")
    public ModelAndView getStory(@PathVariable Integer id) {
        ModelAndView view = new ModelAndView();
        GandongEntity gandongEntity = gandongService.getById(id);
        view.addObject("data", gandongEntity);
//        view.addObject("isVip", TokenManager.isVip(VipType.ENGLISH.getName()));
        view.setViewName("hide/page/oneGandong");
        return view;
    }

    @GetMapping("/pageNum")
    @ResponseBody
    public MessageVo getNum() {
        Long got = gandongService.getCount();
        Long ans = got / pageSize;
        if (got % pageSize != 0) {
            ans += 1;
        }
        return MessageVoUtil.success(ans);
    }
}
