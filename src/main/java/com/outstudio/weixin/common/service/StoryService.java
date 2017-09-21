package com.outstudio.weixin.common.service;

import com.outstudio.weixin.common.dao.StoryEntityMapper;
import com.outstudio.weixin.common.po.StoryEntity;
import com.outstudio.weixin.common.utils.FileUtil;
import com.outstudio.weixin.common.utils.LoggerUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by lmy on 2017/9/9.
 */
@Service
public class StoryService {

    @Resource
    private StoryEntityMapper storyEntityMapper;

    public void addStory(StoryEntity storyEntity) {
        storyEntity.setPost_date(new Date());
        storyEntityMapper.insert(storyEntity);
    }

    public int deleteStoryById(Integer id) {
        StoryEntity storyEntity = getStoryById(id);

        int changedNum = storyEntityMapper.deleteByPrimaryKey(id);
        if (changedNum != 0) {
            FileUtil.deleteFileByUrlPath(storyEntity.getSrc());
        }
        return changedNum;
    }

    public StoryEntity getStoryById(Integer id) {
        return storyEntityMapper.selectByPrimaryKey(id);
    }

    public List<StoryEntity> getStoryByType(Integer type) {
        return storyEntityMapper.selectByType(type);
    }

    public int modifyStory(StoryEntity storyEntity) {
        return storyEntityMapper.updateByPrimaryKeySelective(storyEntity);
    }

    public List<StoryEntity> getAllStories() {
        return storyEntityMapper.selectAll();
    }

    public List<StoryEntity> getStoriesBySearchParam(String searchParam) {
        return storyEntityMapper.selectBySearchParam(searchParam);
    }

    public Long getCount() {
        Long ans = storyEntityMapper.selectCount();
        LoggerUtil.fmtDebug(getClass(), "number is %s : ", ans);
        return ans;
    }
}
