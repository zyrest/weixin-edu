package com.outstudio.weixin.common.service;

import com.outstudio.weixin.common.dao.InterviewVideoEntityMapper;
import com.outstudio.weixin.common.po.InterviewVideoEntity;
import com.outstudio.weixin.common.utils.FileUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by lmy on 2017/9/10.
 */
@Service
public class InterviewVideoService {

    @Resource
    private InterviewVideoEntityMapper interviewVideoEntityMapper;

    public int addInterviewVideo(InterviewVideoEntity interviewVideoEntity) {
        interviewVideoEntity.setPost_date(new Date());
        return interviewVideoEntityMapper.insert(interviewVideoEntity);
    }

    public int deleteInterviewVideo(Integer id) {
        InterviewVideoEntity interviewVideoEntity = getInterviewVideoById(id);

        int changedNum = interviewVideoEntityMapper.deleteByPrimaryKey(id);
        if (changedNum != 0) {
            FileUtil.deleteFileByUrlPath(interviewVideoEntity.getSrc());
        }
        return changedNum;
    }

    public int modifyInterviewVideo(InterviewVideoEntity interviewVideoEntity) {
        return interviewVideoEntityMapper.updateByPrimaryKeySelective(interviewVideoEntity);
    }

    public InterviewVideoEntity getInterviewVideoById(Integer id) {
        return interviewVideoEntityMapper.selectByPrimaryKey(id);
    }

    public List<InterviewVideoEntity> getAllInterviewVideos() {
        return interviewVideoEntityMapper.selectAll();
    }

    public List<InterviewVideoEntity> getInterviewVideosByStage(Integer stage) {
        return interviewVideoEntityMapper.selectByStage(stage);
    }

    public List<InterviewVideoEntity> getBySearchParam(String searchParam) {
        return interviewVideoEntityMapper.selectBySearchParam(searchParam);
    }

    public Long getCount() {
        return interviewVideoEntityMapper.selectCount();
    }
}
