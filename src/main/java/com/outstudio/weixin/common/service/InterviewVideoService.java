package com.outstudio.weixin.common.service;

import com.alibaba.fastjson.JSONObject;
import com.outstudio.weixin.cloud.util.CloudUtil;
import com.outstudio.weixin.common.dao.InterviewVideoEntityMapper;
import com.outstudio.weixin.common.po.InterviewVideoEntity;
import com.outstudio.weixin.common.utils.FileUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
        JSONObject object = CloudUtil.deleteVodFile(interviewVideoEntity.getFileid());

        Integer code = object.getInteger("code");

        int changedNum = 0;
        if (code == 0) {
            changedNum = interviewVideoEntityMapper.deleteByPrimaryKey(id);
        }
//        if (changedNum != 0) {
//            FileUtil.deleteFileByUrlPath(interviewVideoEntity.getSrc());
//        }
                /*
        视频存储改为存在腾讯视频云上
         */

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

    public Set<Integer> getStage() {
        return interviewVideoEntityMapper.selectStage();
    }
}
