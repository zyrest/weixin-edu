package com.outstudio.weixin.common.service;

import com.outstudio.weixin.common.dao.UserEntityMapper;
import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.common.utils.DateUtil;
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
        return userEntityMapper.updateByIdSelective(userEntity);
    }

    public long getCountsByPid(Integer pid) {return userEntityMapper.getCountsByPid(pid);}

    public long getCounts() {return userEntityMapper.getCounts();}
}
