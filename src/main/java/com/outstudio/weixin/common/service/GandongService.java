package com.outstudio.weixin.common.service;

import com.github.pagehelper.PageHelper;
import com.outstudio.weixin.common.dao.GandongEntityMapper;
import com.outstudio.weixin.common.po.GandongEntity;
import com.outstudio.weixin.common.utils.FileUtil;
import com.outstudio.weixin.common.utils.LoggerUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by ZhouYing.
 * www.zhouying.xyz
 */
@Service
public class GandongService {

    @Resource
    private GandongEntityMapper gandongEntityMapper;

    public void add(GandongEntity gandongEntity) {
        gandongEntity.setPost_date(new Date());
        gandongEntityMapper.insert(gandongEntity);
    }

    public int deleteById(Integer id) {
        GandongEntity gandongEntity = getById(id);

        int changedNum = gandongEntityMapper.deleteByPrimaryKey(id);
        if (changedNum != 0) {
            FileUtil.deleteFileByUrlPath(gandongEntity.getSrc());
        }
        return changedNum;
    }

    public GandongEntity getById(Integer id) {
        return gandongEntityMapper.selectByPrimaryKey(id);
    }

    public int modify(GandongEntity gandongEntity) {
        return gandongEntityMapper.updateByPrimaryKeySelective(gandongEntity);
    }

    public List<GandongEntity> getAllPageable(int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        return gandongEntityMapper.selectAll();
    }

    public Long getCount() {
        Long ans = gandongEntityMapper.selectCount();
        LoggerUtil.fmtDebug(getClass(), "number is %s : ", ans);
        return ans;
    }
}
