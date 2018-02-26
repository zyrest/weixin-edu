package com.outstudio.weixin.common.service;

import com.alibaba.fastjson.JSONObject;
import com.outstudio.weixin.common.cloud.util.CloudUtil;
import com.outstudio.weixin.common.dao.ExhibitionEntityMapper;
import com.outstudio.weixin.common.dao.UpvoteEntityMapper;
import com.outstudio.weixin.common.po.ExhibitionEntity;
import com.outstudio.weixin.common.po.UpvoteEntity;
import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.common.utils.FileUtil;
import com.outstudio.weixin.common.utils.LoggerUtil;
import com.outstudio.weixin.common.vo.ExhibitionType;
import com.outstudio.weixin.core.shiro.token.TokenManager;
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

    public void add(ExhibitionEntity entity, String type, Integer verified) {
        if (TokenManager.isManager())
            entity.setUser_id(0);
        else
            entity.setUser_id(TokenManager.getWeixinToken().getId());

        entity.setPost_date(new Date());
        entity.setType(type);
        entity.setVerified(verified);
        try {
            exhibitionEntityMapper.insertSelective(entity);
        } catch (Exception e) {
            LoggerUtil.error(getClass(), "插入数据库错误，可能是插入参数为空", e);
        }
    }

    public int deleteById(Integer id) {
        ExhibitionEntity entity = getById(id);
        if (ExhibitionType.VIDEO.getType().equalsIgnoreCase(entity.getType()))
            return deleteVideo(entity);
        else
            return deleteAudioOrPicture(entity);
    }

    public Integer deleteVideo(ExhibitionEntity entity) {
        String fileid = entity.getFileid();
        JSONObject object = CloudUtil.deleteVodFile(fileid);

        Integer code = object.getInteger("code");

        int changedNum = 0;
        if (code == 0) {
            changedNum = exhibitionEntityMapper.deleteByPrimaryKey(entity.getId());
        }

        return changedNum;
    }

    public int deleteAudioOrPicture(ExhibitionEntity entity) {
        int changedNum = exhibitionEntityMapper.deleteByPrimaryKey(entity.getId());
        if (changedNum != 0) {
            FileUtil.deleteFileByUrlPath(entity.getSrc());
        }
        return changedNum;
    }

    public ExhibitionEntity getById(Integer id) {
        return exhibitionEntityMapper.selectByPrimaryKey(id);
    }

    public ExhibitionEntity getById(Integer id, Integer verified) {
        return exhibitionEntityMapper.selectByPrimaryKey(id, verified);
    }

    public List<ExhibitionEntity> getAllByType(String type, Integer verified) {
        return exhibitionEntityMapper.selectAllByType(type, verified);
    }

    public List<ExhibitionEntity> getByVerified(Integer verified) {
        return exhibitionEntityMapper.selectByVerified(verified);
    }

    public List<ExhibitionEntity> getBySearchParam(String searchParam, Integer verified) {
        return exhibitionEntityMapper.selectBySearchParam(searchParam, verified);
    }

    public int getCountByType(String type, Integer verified) {
        return exhibitionEntityMapper.count(type, verified);
    }

    public int count(Integer verified) {
        return exhibitionEntityMapper.count(verified);
    }

    public boolean upvote(Integer id, String ip, Integer verified) {
        UserEntity currentUser = TokenManager.getWeixinToken();
        UpvoteEntity upvoteEntity = upvoteEntityMapper.selectByExhibitionIdAndOpenid(id, currentUser.getOpenid());
        if (upvoteEntity == null) {
            upvoteEntity = new UpvoteEntity();
            upvoteEntity.setExhibition_id(id);
            upvoteEntity.setOpenid(currentUser.getOpenid());
            upvoteEntity.setVoted(1);
            upvoteEntity.setIp(ip);
            upvoteEntityMapper.insertSelective(upvoteEntity);

            ExhibitionEntity exhibitionEntity = exhibitionEntityMapper.selectByPrimaryKey(id, verified);
            exhibitionEntity.setUpvote(exhibitionEntity.getUpvote() + 1);
            exhibitionEntityMapper.updateByPrimaryKey(exhibitionEntity);
            return true;
        } else {
            return false;
        }
    }

    public int verify(Integer id) {
        ExhibitionEntity entity = getById(id, 0);
        entity.setVerified(1);
        return exhibitionEntityMapper.updateByPrimaryKeySelective(entity);
    }

    public List<ExhibitionEntity> getByUserId() {
        UserEntity userEntity = TokenManager.getWeixinToken();
        return exhibitionEntityMapper.selectByUserId(userEntity.getId());
    }
}
