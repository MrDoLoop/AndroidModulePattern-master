package com.netease.pineapple.common.utils;

import android.text.TextUtils;

import com.netease.pineapple.common.contants.TimeConstant;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

/**
 * 字符串相关工具类
 */
public class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 字符串拼接,线程安全
     */
    public static String buffer(String... array) {
        StringBuffer s = new StringBuffer();
        for (String str : array) {
            s.append(str);
        }
        return s.toString();
    }

    /**
     * 字符串拼接,线程不安全,效率高
     */
    public static String builder(String... array) {
        StringBuilder s = new StringBuilder();
        for (String str : array) {
            s.append(str);
        }
        return s.toString();
    }

    /**
     * 判断两字符串是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equalsIgnoreCase(String a, String b) {
        return (a == b) || (b != null) && (a.length() == b.length()) && a.regionMatches(true, 0, b, 0, b.length());
    }

    /**
     * 获取小数点后一位
     * @return
     */
    public static String getViewCountStr(String viewCount, String str) {
        if(TextUtils.isEmpty(viewCount)) {
            return "";
        }

        try {
            long num = Long.parseLong(viewCount);
            if(num >= 10000) {
                return division(num, 10000) + "w "+ str;
            } else if(num >= 1000) {
                return division(num, 1000) + "k "+ str;
            }
        } catch (Exception e) {

        }
        return viewCount + " " + str;
    }

    //整数相除 保留一位小数
    private static String division(long a ,long b){

        String result = "";
        if(b == 0) return result;

        float num =(float) a / b;

        BigDecimal bd = new BigDecimal(num);
        bd = bd.setScale(1, RoundingMode.HALF_UP);

        result = bd.toString();
        if(result.endsWith("0")) {
            result = result.substring(0,result.indexOf("."));
        }
        return result;
    }

    public static final String getDurationString(long duration) {
        long second = duration % TimeConstant.TIME_MINUTE_TO_SECOND;
        long minute = duration / TimeConstant.TIME_MINUTE_TO_SECOND;
        long hour = minute / TimeConstant.TIME_HOUR_TO_MINUTE;
        minute = minute % TimeConstant.TIME_HOUR_TO_MINUTE;
        if (hour > 0) {
            return String.format(Locale.getDefault(), "%1$02d:%2$02d:%3$02d", hour, minute, second);
        } else {
            return String.format(Locale.getDefault(), "%1$02d:%2$02d", minute, second);
        }
    }

}