package com.outstudio.weixin.page.controller;

import com.github.pagehelper.PageHelper;
import com.outstudio.weixin.common.po.StoryEntity;
import com.outstudio.weixin.common.service.StoryService;
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
@Controller("storyPageController")
@RequestMapping("/page")
public class StoryController {

    private static final int pageSize = 10;

    private static final String REDIRECT_URL = "";
    @Resource
    private StoryService storyService;

    @GetMapping("/stories/type/{type}/page/{pageNum}")
    @ResponseBody
    public MessageVo getStotiesByType(@PathVariable Integer type,
                                      @PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<StoryEntity> storyEntities = storyService.getStoryByType(type);
        return MessageVoUtil.success(REDIRECT_URL, storyEntities);
    }

    @GetMapping("/stories/page/{pageNum}")
    @ResponseBody
    public MessageVo getAllStories(@PathVariable Integer pageNum) {
//        if (pageNum > getTotalPageNum()) {
//            return MessageVoUtil.resourceNotFound("");
//        }
        PageHelper.startPage(pageNum, pageSize);
        List<StoryEntity> storyEntities = storyService.getAllStories();
        return MessageVoUtil.success(REDIRECT_URL, storyEntities);
    }

    @GetMapping("/stories/{id}")
    public ModelAndView getStory(@PathVariable Integer id) {
        ModelAndView view = new ModelAndView();
        StoryEntity storyEntity = storyService.getStoryById(id);
        view.addObject("data", storyEntity);
//        view.addObject("isVip", TokenManager.isVip("english"));
        view.setViewName("hide/page/audioOne");
        return view;
    }

    @GetMapping("/stories")
    @ResponseBody
    public MessageVo searchStories(@RequestParam("searchParam") String searchParam) {
        List<StoryEntity> storyEntities = storyService.getStoriesBySearchParam(searchParam);
        return MessageVoUtil.success(REDIRECT_URL, storyEntities);
    }

    @GetMapping("/stories/pageNum")
    @ResponseBody
    public MessageVo getNum() {

        return MessageVoUtil.success(getTotalPageNum());
    }

    private Long getTotalPageNum() {
        Long got = storyService.getCount();
        Long ans = got / pageSize;
        if (got % pageSize != 0) {
            ans += 1;
        }

        return ans;
    }
}
