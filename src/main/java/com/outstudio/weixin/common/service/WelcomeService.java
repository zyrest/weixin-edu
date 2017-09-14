package com.outstudio.weixin.common.service;

import com.outstudio.weixin.common.dao.WelcomeEntityMapper;
import com.outstudio.weixin.common.po.WelcomeEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lmy on 2017/9/14.
 */
@Service
public class WelcomeService {
    @Resource
    private WelcomeEntityMapper welcomeEntityMapper;

    public List<WelcomeEntity> getAll() {
        return welcomeEntityMapper.selectAll();
    }

    public WelcomeEntity getById(Integer id) {
        return welcomeEntityMapper.selectByPrimaryKey(id);
    }

    public int post(WelcomeEntity welcomeEntity) {
        WelcomeEntity oldWelcomeEntity = getByIsUsing(1);
        welcomeEntityMapper.updateIsUsingById(oldWelcomeEntity.getId(), 0);
        welcomeEntity.setIsUsing(1);

        return welcomeEntityMapper.insert(welcomeEntity);
    }

    public int deleteById(Integer id) {
        if (getById(id).getIsUsing().intValue()==1) {
            return 0;
        }
        return welcomeEntityMapper.deleteByPrimaryKey(id);
    }

    public int modifyById(WelcomeEntity welcomeEntity) {
        return welcomeEntityMapper.updateByPrimaryKey(welcomeEntity);
    }

    public int useById(Integer id) {
        WelcomeEntity welcomeEntity = getByIsUsing(1);
        welcomeEntityMapper.updateIsUsingById(welcomeEntity.getId(), 0);
        return welcomeEntityMapper.updateIsUsingById(id, 1);
    }

    public WelcomeEntity getByIsUsing(Integer isUsing) {
        return welcomeEntityMapper.selectByUsing(isUsing);
    }
}
