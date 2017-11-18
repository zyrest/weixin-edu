package com.outstudio.weixin.common.service;

import com.outstudio.weixin.back.exception.InvalidRequestException;
import com.outstudio.weixin.common.dao.ChargeEntityMapper;
import com.outstudio.weixin.common.po.ChargeEntity;
import com.outstudio.weixin.common.po.UserEntity;
import com.outstudio.weixin.common.utils.DateUtil;
import com.outstudio.weixin.common.utils.LoggerUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
                       String transaction_id,
                       Date now_date,
                       String total_fee) {

        Integer pid = checkPreCharge(openid);

        int days = 30;
//        if ("3000".equalsIgnoreCase(total_fee)) {
//            days = 30;
//        } else if ("30000".equalsIgnoreCase(total_fee)) {
//            days = 365;
//        }


        UserEntity userEntity = userService.getUserByOpenId(openid);
        Date vip_end_date = userEntity.getVip_end_date();
        if (DateUtil.isNotExpire(vip_end_date)) {
            userEntity.setVip_end_date(DateUtil.dateAdd(vip_end_date, days));
        } else {
            userEntity.setVip_end_date(DateUtil.dateAdd(new Date(), days));
        }

        userEntity.setPid(pid);

        //检查前端传过来的pid是否合法，即pid是否存在
        UserEntity pUser = userService.getUserById(pid);
        if (pid != 0 && pUser == null) {
            LoggerUtil.error(getClass(), "非法请求，pid不存在");
            userEntity.setPid(0);
        }

        userService.updateUser(userEntity);

        if (userEntity.getPid() != 0) {
            userService.addBalance(userEntity.getPid(), Integer.parseInt(total_fee) / 100);
        }

        ChargeEntity chargeEntity = new ChargeEntity();
        chargeEntity.setOpenid(openid);
        chargeEntity.setNow_date(now_date);
        chargeEntity.setDates(days);
        chargeEntity.setOut_trade_no(out_trade_no);
        chargeEntity.setTransaction_id(transaction_id);
        chargeEntity.setPrepared(0);
        chargeEntity.setToPid(pid);

        chargeEntityMapper.updateByOpenidSelective(chargeEntity);
    }

    private Integer checkPreCharge(String openid) {
        ChargeEntity chargeEntity = chargeEntityMapper.getByOpenid(openid);

        if (chargeEntity == null) {
            LoggerUtil.error(getClass(), "用户没有完成预支付流程");
            throw new InvalidRequestException("非法请求");
        }

        Integer prepared = chargeEntity.getPrepared();
        if (prepared != 1) {
            LoggerUtil.error(getClass(), "用户没有完成预支付流程");
            throw new InvalidRequestException("非法请求");
        } else {
            return chargeEntity.getToPid();
        }

    }

    public void preCharge(String openid, Integer pid) {
        ChargeEntity chargeEntity = chargeEntityMapper.getByOpenid(openid);

        if (chargeEntity == null) {
            chargeEntity = new ChargeEntity();
            chargeEntity.setOpenid(openid);
            chargeEntity.setPrepared(1);
            chargeEntity.setToPid(pid);
            chargeEntity.setNow_date(new Date());
            chargeEntityMapper.insertSelective(chargeEntity);
        } else {
            chargeEntity.setPrepared(1);
            chargeEntity.setToPid(pid);
            chargeEntity.setNow_date(new Date());
            chargeEntityMapper.updateByOpenidSelective(chargeEntity);
        }
    }
}
