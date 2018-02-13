package com.outstudio.weixin.common.service;

import com.outstudio.weixin.common.dao.EnReviewEntityMapper;
import com.outstudio.weixin.common.po.EnReviewEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by lmy on 2017/9/10.
 */
@Service
public class EnReviewService {

    @Resource
    private EnReviewEntityMapper enReviewEntityMapper;

    public int addEnReview(EnReviewEntity enReviewEntity) {
        enReviewEntity.setPost_date(new Date());

        return enReviewEntityMapper.insert(enReviewEntity);
    }

    public int deleteEnReviewById(Integer id) {
        return enReviewEntityMapper.deleteByPrimaryKey(id);
    }

    public int modifyEnReview(EnReviewEntity enReviewEntity) {
        return enReviewEntityMapper.updateByPrimaryKeySelective(enReviewEntity);
    }

    public EnReviewEntity getEnReviewById(Integer id) {
        return enReviewEntityMapper.selectByPrimaryKey(id);
    }

    public List<EnReviewEntity> getAll(String type) {
        return enReviewEntityMapper.selectAll(type);
    }

    public List<EnReviewEntity> getByStage(Integer stage,String type) {
        return enReviewEntityMapper.selectByStage(stage,type);
    }

    public int modifyReviewExceptContent(EnReviewEntity enReviewEntity) {
        return enReviewEntityMapper.updateByPrimaryKey(enReviewEntity);
    }

    public List<EnReviewEntity> getBySearchParam(String searchParam, String type) {
        return enReviewEntityMapper.selectBySearchParam(searchParam, type);
    }

    public Set<Integer> getStage(String type) {
        return enReviewEntityMapper.selectStage(type);
    }

    public Long getCount(String type) {
        return enReviewEntityMapper.selectCount(type);
    }
}
