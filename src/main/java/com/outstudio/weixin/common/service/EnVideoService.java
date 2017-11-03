package com.outstudio.weixin.common.service;

import com.outstudio.weixin.common.dao.EnVideoEntityMapper;
import com.outstudio.weixin.common.po.EnVideoEntity;
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
public class EnVideoService {

    @Resource
    private EnVideoEntityMapper enVideoEntityMapper;

    public int addEnVideo(EnVideoEntity enVideoEntity) {
        enVideoEntity.setPost_date(new Date());
        return enVideoEntityMapper.insert(enVideoEntity);
    }

    public int deleteEnVideo(Integer id) {
        EnVideoEntity enVideoEntity = getEnVideoById(id);

        int changedNum = enVideoEntityMapper.deleteByPrimaryKey(id);
        if (changedNum != 0) {
            FileUtil.deleteFileByUrlPath(enVideoEntity.getSrc());
        }
        return changedNum;
    }

    public int modifyEnVideo(EnVideoEntity enVideoEntity) {
        return enVideoEntityMapper.updateByPrimaryKeySelective(enVideoEntity);
    }

    public EnVideoEntity getEnVideoById(Integer id) {
        return enVideoEntityMapper.selectByPrimaryKey(id);
    }

    public List<EnVideoEntity> getAllEnVideos() {
        return enVideoEntityMapper.selectAll();
    }

    public List<EnVideoEntity> getEnVideosByStage(Integer stage) {
        return enVideoEntityMapper.selectByStage(stage);
    }

    public List<EnVideoEntity> getEnVideosBySearchParam(String searchParam) {
        return enVideoEntityMapper.selectBySearchParam(searchParam);
    }

    public Set<Integer> getStage() {
        return enVideoEntityMapper.selectStage();
    }

    public Long getCount() {}
}
