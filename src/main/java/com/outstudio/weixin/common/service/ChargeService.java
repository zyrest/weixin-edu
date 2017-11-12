package com.outstudio.weixin.common.service;

import com.outstudio.weixin.common.dao.ChargeEntityMapper;
import com.outstudio.weixin.common.po.ChargeEntity;
import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.common.utils.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by lmy on 2017/9/12.
 */
@Service
@Transactional
public class ChargeService {
    @Resource
    private ChargeEntityMapper chargeEntityMapper;
    @Resource
    private UserService userService;

    public void charge(String openid,
                       String out_trade_no,
                       Date now_date,
                       String total_fee) {
        int days = 0;
        if ("3000".equalsIgnoreCase(total_fee)) {
            days = 30;
        } else if ("30000".equalsIgnoreCase(total_fee)) {
            days = 365;
        }
        UserEntity userEntity = userService.getUserByOpenId(openid);
        Date vip_end_date = userEntity.getVip_end_date();
        if (DateUtil.isNotExpire(vip_end_date)) {
            userEntity.setVip_end_date(DateUtil.dateAdd(vip_end_date, days));
        } else {
            userEntity.setVip_end_date(DateUtil.dateAdd(new Date(), days));
        }
        userService.updateUser(userEntity);

        if (userEntity.getPid() != 0) {
            userService.addBalance(userEntity.getPid(), Integer.parseInt(total_fee)/100);
        }

        ChargeEntity chargeEntity = new ChargeEntity();
        chargeEntity.setOpenid(openid);
        chargeEntity.setNow_date(now_date);
        chargeEntity.setDates(365);
        chargeEntity.setOut_trade_no(out_trade_no);

        chargeEntityMapper.insertSelective(chargeEntity);
    }
    public void charge(String openid,
                       String out_trade_no,
                       Date now_date,
                       String total_fee,
                       Integer pid) {
        charge(openid, out_trade_no, now_date, total_fee);
        double fee = Integer.parseInt(total_fee)/100;
        userService.addBalance(pid, fee);
    }

    public void charge(String openid, String transaction_id) {
        ChargeEntity chargeEntity = chargeEntityMapper.getByOpenid(openid);
        chargeEntity.setTransaction_id(transaction_id);
        chargeEntityMapper.updateByPrimaryKeySelective(chargeEntity);
    }
}
