package com.outstudio.weixin.common.service;

import com.outstudio.weixin.common.dao.ExhibitionEntityMapper;
import com.outstudio.weixin.common.dao.UpvoteEntityMapper;
import com.outstudio.weixin.common.po.ExhibitionEntity;
import com.outstudio.weixin.common.po.UpvoteEntity;
import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.core.shiro.token.TokenManager;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by lmy on 2018/2/18.
 */
@Service
public class ExhibitionService {

    @Resource
    private ExhibitionEntityMapper exhibitionEntityMapper;

    @Resource
    private UpvoteEntityMapper upvoteEntityMapper;

    public void backAdd(ExhibitionEntity entity, String type) {
        entity.setPost_date(new Date());
        entity.setType(type);
        entity.setVerified(1);
        exhibitionEntityMapper.insertSelective(entity);
    }

    public Integer deleteById(Integer id) {
        return exhibitionEntityMapper.deleteByPrimaryKey(id);
    }

    public ExhibitionEntity getById(Integer id) {
        return exhibitionEntityMapper.selectByPrimaryKey(id);
    }

    public List<ExhibitionEntity> getAllByType(String type) {
        return exhibitionEntityMapper.selectAllByType(type);
    }

    public List<ExhibitionEntity> getBySearchParam(String searchParam) {
        return exhibitionEntityMapper.selectBySearchParam(searchParam);
    }

    public int getCountByType(String type) {
        return exhibitionEntityMapper.count(type);
    }

    public boolean upvote(Integer id,String ip) {
        UserEntity currentUser = TokenManager.getWeixinToken();
        UpvoteEntity upvoteEntity = upvoteEntityMapper.selectByExhibitionIdAndOpenid(id, currentUser.getOpenid());
        if (upvoteEntity == null) {
            upvoteEntity = new UpvoteEntity();
            upvoteEntity.setExhibition_id(id);
            upvoteEntity.setOpenid(currentUser.getOpenid());
            upvoteEntity.setVoted(1);
            upvoteEntity.setIp(ip);
            upvoteEntityMapper.insertSelective(upvoteEntity);

            ExhibitionEntity exhibitionEntity = exhibitionEntityMapper.selectByPrimaryKey(id);
            exhibitionEntity.setUpvote(exhibitionEntity.getUpvote() + 1);
            exhibitionEntityMapper.updateByPrimaryKey(exhibitionEntity);
            return true;
        } else {
            return false;
        }
    }
}
