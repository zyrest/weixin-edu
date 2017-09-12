package com.outstudio.weixin.common.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by 96428 on 2017/7/19.
 * This in TestWeixin, samson.common.utils
 */
public class DateUtil {

    public static Timestamp sqlDayStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return new Timestamp(calendar.getTimeInMillis());
    }

    public static Timestamp sqlDayEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 1000);

        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 在给定的日期基础上往后推迟给定的天数
     * @param baseDate 给定的日期
     * @param addNum 要推迟的天数
     * @return 推迟之后的日期
     */
    public static Date dateAdd(Date baseDate, int addNum) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(baseDate);
        calendar.add(calendar.DATE, addNum);//把日期往后增加addNum天.整数往后推,负数往前移动
        baseDate = calendar.getTime();   //这个时间就是日期往后推一天的结果
        return baseDate;
    }

    public static Date createDateByStr(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date ans = null;
        try {
            ans = dateFormat.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ans;
    }

    public static boolean between(Date now, Date start, Date end) {
        return now != null && now.after(start) && now.before(end);
    }

    public static boolean isNotExpire(Date date) {
        return date != null && date.after(new Date());
    }
}
