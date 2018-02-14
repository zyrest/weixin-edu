package com.outstudio.weixin.common.service;

import com.outstudio.weixin.common.dao.UserEntityMapper;
import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.common.utils.DateUtil;
import com.outstudio.weixin.common.utils.LoggerUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by 96428 on 2017/9/6.
 * This in weixin-edu, com.outstudio.weixin.common.service
 */
@Service
public class UserService {

    @Resource
    private UserEntityMapper userEntityMapper;

    public UserEntity getUserByOpenId(String openid) {
        return userEntityMapper.selectByPrimaryKey(openid);
    }

    public void saveUser(UserEntity user) {
        user.setVip_end_date(new Date());
        user.setMath_end_date(new Date());
        user.setChemistry_end_date(new Date());
        user.setPhysics_end_date(new Date());
        userEntityMapper.insertSelective(user);
    }

    public boolean checkExpired(String openid) {
        UserEntity userEntity = userEntityMapper.selectByPrimaryKey(openid);
        return DateUtil.isNotExpire(userEntity.getVip_end_date());
    }

    public void updateUser(UserEntity userEntity) {
        userEntityMapper.updateByPrimaryKeySelective(userEntity);
    }

    public List<UserEntity> getAllUsers() {
        return userEntityMapper.getAllUsers();
    }

    public int setAgent(Integer id, Integer level) {
        return userEntityMapper.setAgent(id, level);
    }

    public int setBalance(Integer id, double balance) {
        return userEntityMapper.setBalance(id, balance);
    }

    public UserEntity getUserById(Integer id) {
        return userEntityMapper.getUserById(id);
    }

    public int addBalance(Integer id, double fee) {
        UserEntity userEntity = getUserById(id);
        if (userEntity == null) {
            LoggerUtil.error(getClass(), "用户的pid不存在");
            return -1;
        }

        double balance = userEntity.getBalance();
        int level = userEntity.getLevel();
        if (level == 0) {
            balance = balance + 0;
        } else if (level == 1) {
            balance += fee * 0.2;
        } else if (level == 2) {
            balance += fee * 0.5;
        } else if (level == 3) {
            balance += fee * 0.7;
        }

        userEntity.setBalance(balance);

        __addBalance(userEntity.getPid(), fee, userEntity.getLevel());

        return userEntityMapper.updateByIdSelective(userEntity);
    }


    /**
     * 通过递归一层层的给上级提成
     *
     * @param pid   上级用户id
     * @param fee   当前用户交易价格
     * @param level 当前用户代理等级
     */
    private void __addBalance(Integer pid, double fee, Integer level) {
        if (pid == 0) {
            return;
        }

        if (level == 0) {
            return;
        }

        UserEntity userEntity = getUserById(pid);

        if (level >= userEntity.getLevel()) {
            return;
        }

        double balance = userEntity.getBalance();
        balance += fee * 0.2;
        userEntity.setBalance(balance);
        userEntityMapper.updateByIdSelective(userEntity);

        __addBalance(userEntity.getPid(), fee, userEntity.getLevel());
    }

    public long getCountsByPid(Integer pid) {
        return userEntityMapper.getCountsByPid(pid);
    }

    public long getCounts() {
        return userEntityMapper.getCounts();
    }

    public int editUser(UserEntity userEntity) {
        return userEntityMapper.updateByIdSelective(userEntity);
    }

    public void setPid(Integer pid, String openid) {
        userEntityMapper.setPid(pid, openid);
    }

    public void updateUserDate(String openid, int days) {
        UserEntity userEntity = getUserByOpenId(openid);

        Date vip_end_date = userEntity.getVip_end_date();
        if (DateUtil.isNotExpire(vip_end_date)) {
            userEntity.setVip_end_date(DateUtil.dateAdd(vip_end_date, days));
        } else {
            userEntity.setVip_end_date(DateUtil.dateAdd(new Date(), days));
        }

        updateUser(userEntity);

    }

    public void updateParentBalance(String openid, String total_fee) {
        Integer pid = getUserByOpenId(openid).getPid();
        if (pid != 0) {
            addBalance(pid, Integer.parseInt(total_fee) / 100);
        }
    }
}
