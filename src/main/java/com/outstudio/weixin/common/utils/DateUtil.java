package com.outstudio.weixin.common.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
}
