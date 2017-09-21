package com.outstudio.weixin.page.controller;

import com.github.pagehelper.PageHelper;
import com.outstudio.weixin.common.po.StoryEntity;
import com.outstudio.weixin.common.service.StoryService;
import com.outstudio.weixin.common.utils.MessageVoUtil;
import com.outstudio.weixin.common.vo.MessageVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lmy on 2017/9/11.
 */
@RestController("storyPageController")
@RequestMapping("/page")
public class StoryController {

    private static final int pageSize = 10;

    private static final String REDIRECT_URL = "";
    @Resource
    private StoryService storyService;

    @GetMapping("/stories/type/{type}/page/{pageNum}")
    public MessageVo getStotiesByType(@PathVariable Integer type,
                                      @PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<StoryEntity> storyEntities = storyService.getStoryByType(type);
        return MessageVoUtil.success(REDIRECT_URL, storyEntities);
    }

    @GetMapping("/stories/page/{pageNum}")
    public MessageVo getAllStories(@PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<StoryEntity> storyEntities = storyService.getAllStories();
        return MessageVoUtil.success(REDIRECT_URL, storyEntities);
    }

    @GetMapping("/stories/{id}")
    public MessageVo getStory(@PathVariable Integer id) {
        StoryEntity storyEntity = storyService.getStoryById(id);
        return MessageVoUtil.success(REDIRECT_URL, storyEntity);
    }

    @GetMapping("/stories")
    public MessageVo searchStories(@RequestParam("searchParam") String searchParam) {
        List<StoryEntity> storyEntities = storyService.getStoriesBySearchParam(searchParam);
        return MessageVoUtil.success(REDIRECT_URL, storyEntities);
    }

    @GetMapping("/stories/pageNum")
    public MessageVo getNum() {
        Long got = storyService.getCount();
        Long ans = got / pageSize;
        if (got % pageSize != 0) {
            ans += 1;
        }
        return MessageVoUtil.success(ans);
    }
}
