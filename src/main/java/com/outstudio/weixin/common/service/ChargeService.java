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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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

    private void charge(String openid,
                        String out_trade_no,
                        String transaction_id,
                        Date now_date,
                        String total_fee) {

        checkPreCharge(openid);

        int days = 0;
        if ("3000".equalsIgnoreCase(total_fee)) {
            days = 30;
        } else if ("30000".equalsIgnoreCase(total_fee)) {
            days = 365;
        }

        userService.updateUserDate(openid, days);
        userService.updateParentBalance(openid,total_fee);
        updateChargeHistory(openid, days, out_trade_no, transaction_id, now_date);
    }

    private void updateChargeHistory(String openid,
                                     Integer days,
                                     String out_trade_no,
                                     String transaction_id,
                                     Date now_date) {

        ChargeEntity chargeEntity = new ChargeEntity();
        chargeEntity.setOpenid(openid);
        chargeEntity.setNow_date(now_date);
        chargeEntity.setDates(days);
        chargeEntity.setOut_trade_no(out_trade_no);
        chargeEntity.setTransaction_id(transaction_id);
        chargeEntity.setPrepared(0);

        chargeEntityMapper.updateByOpenidSelective(chargeEntity);
    }

    private void checkPreCharge(String openid) {
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
            return;
        }

    }

    public void preCharge(String openid) {
        ChargeEntity chargeEntity = chargeEntityMapper.getByOpenid(openid);

        if (chargeEntity == null) {
            chargeEntity = new ChargeEntity();
            chargeEntity.setOpenid(openid);
            chargeEntity.setPrepared(1);
            chargeEntity.setNow_date(new Date());
            chargeEntityMapper.insertSelective(chargeEntity);
        } else {
            chargeEntity.setPrepared(1);
            chargeEntity.setNow_date(new Date());
            chargeEntityMapper.updateByOpenidSelective(chargeEntity);
        }
    }

    /**
     * 处理返回的xml信息,存进数据库之前数据的处理
     * @param map
     */
    public void charge(Map<String, String> map) {
        String openid = map.get("openid");
        String transaction_id = map.get("transaction_id");
        String out_trade_no = map.get("out_trade_no");
        String now_date = map.get("time_end");
        String total_fee = map.get("total_fee");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = null;
        try {
            date = sdf.parse(now_date);
            LoggerUtil.fmtDebug(getClass(),"格式化后的交易日期为%s",date.toString());
        } catch (ParseException e) {
            LoggerUtil.error(getClass(), "日期格式转化错误", e);
        }

        charge(openid, out_trade_no,transaction_id, date, total_fee);
    }
}
