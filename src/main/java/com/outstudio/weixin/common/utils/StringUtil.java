package com.outstudio.weixin.common.utils;

/**
 * Created by 96428 on 2017/7/13.
 */
public class StringUtil {

    public static String array2String(String[] array) {
        StringBuilder str = new StringBuilder();

        for (String tmp : array) {
            str.append(tmp);
        }

        return str.toString();
    }

    public static Boolean isBlank(String str) {

        return str == null || str.trim().equals("");
    }

    public static boolean lengthBetween(String str, int min, int max) {
        return str != null && str.length() >= min && str.length() <= max;
    }
}
