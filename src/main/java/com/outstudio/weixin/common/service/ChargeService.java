package com.outstudio.weixin.common.service;

import com.outstudio.weixin.common.dao.ChargeEntityMapper;
import com.outstudio.weixin.common.po.ChargeEntity;
import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.common.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by lmy on 2017/9/12.
 */
@Service
public class ChargeService {
    @Resource
    private ChargeEntityMapper chargeEntityMapper;
    @Resource
    private UserService userService;

    public void charge(String openid) {
        UserEntity userEntity = userService.getUserByOpenId(openid);
        Date vip_end_date = userEntity.getVip_end_date();
        if (DateUtil.isNotExpire(vip_end_date)) {
            userEntity.setVip_end_date(DateUtil.dateAdd(vip_end_date, 365));
        } else {
            userEntity.setVip_end_date(DateUtil.dateAdd(new Date(), 365));
        }

        userService.updateUser(userEntity);

        ChargeEntity chargeEntity = new ChargeEntity();
        chargeEntity.setOpenid(openid);
        chargeEntity.setNow_date(new Date());
        chargeEntity.setDates(365);
        chargeEntityMapper.insertSelective(chargeEntity);
    }
}
