package com.netease.pineapple.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 处理时间转化String格式
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {

    public static final String HOUR_MINUTE_FORMAT = "HH:mm";
    public static final String LONG_DATE_FORMAT = "yyyy-MM-dd";     // 格式：年－月－日

    /**
     * @param time    long类型
     * @param formate
     * @return
     */
    public static String getDate(long time, String formate) {
        try {
            if (TextUtils.isEmpty(formate)) {
                formate = "yyyy年MM月dd日";
            }
            SimpleDateFormat formator = new SimpleDateFormat(formate);
            return formator.format(new Date(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param date    Data类型
     * @param formate
     * @return
     */
    public static String getDate(Date date, String formate) {
        try {
            if (date == null) {
                return "";
            }
            if (TextUtils.isEmpty(formate)) {
                formate = "yyyy年MM月dd日";
            }
            SimpleDateFormat formator = new SimpleDateFormat(formate);
            return formator.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String getTimeFormat(long second) {
        try {
            long hh = second / 3600;
            long mm = second % 3600 / 60;
            long ss = second % 60;
            String stringTemp = "";
            if (0 != hh) {
                stringTemp = String.format("%02d:%02d:%02d", hh, mm, ss);
            } else {
                stringTemp = String.format("%02d:%02d", mm, ss);
            }
            return stringTemp;
        } catch (Exception e) {
            return "";
        }
    }


    /**
     * 相对于当前时间多久
     *
     * @param context
     * @param duration
     * @return
     */
    public static String getDuration(Context context, long duration) {
        if (duration <= 0) {
            return "";
        }
        String minuteStr = "分";//context.getResources().getString(R.string.minute);
        String hourStr = "小时";//context.getResources().getString(R.string.hour);
        String beforeStr = "前";//context.getResources().getString(R.string.before);
        String justStr = "刚刚";//context.getResources().getString(R.string.just);
        try {
            duration = duration / 1000;
            long hour = duration / 3600;
            long min = (duration % 3600) / 60;
            if (hour == 0) {
                if (min > 0)
                    return min + minuteStr + beforeStr;
            } else {
                return hour + hourStr + min + minuteStr + beforeStr;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return justStr;
    }

    /**
     * 距离当前时间多久
     *
     * @param duration
     * @return
     */
    public static String getDurationMs(long duration) {
        if (duration <= 0) {
            return "";
        }
        try {
            duration = duration / 1000;
            long hour = duration / 3600;
            long min = (duration % 3600) / 60;
            long s = (duration % 3600) % 60;
            if (hour >= 48) {
                return (hour / 24) + "天";
            }
            if (hour == 0) {
                if (min == 0) {
                    return s + "秒";
                } else {
                    return min + "分钟" + s + "秒";
                }
            } else {
                return hour + "小时" + min + "分钟" + s + "秒";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 距离当前 天 月
     *
     * @param timestamp
     * @return
     */
    public static String getDurationD_M(long timestamp) {
        if (timestamp <= 0) {
            return "";
        }
        try {
            long now = System.currentTimeMillis();
            long diff = now - timestamp;
            long day = diff / 1000 / 60 / 60 / 24;
            if(day >= 1) {
                return day + "天前";
            } else {
                diff = diff / 1000;
                long hour = diff / 3600;
                long min = (diff % 3600) / 60;
                if (hour == 0) {
                    if (min == 0)
                        return "刚刚";
                    if (min > 0)
                        return min + "分钟前";
                } else {
                    return hour + "小时前";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取格式化时间
     *
     * @param time
     * @return
     */
    public static String getFormateTime(long time) {
        try {
            String formate = "yyyy/MM/dd HH:mm:ss";
            SimpleDateFormat formator = new SimpleDateFormat(formate);
            return formator.format(new Date(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "1970/12/21 12:21:00";

    }

    /**
     * 根据格式格式化时间
     *
     * @param time
     * @param formate
     * @return
     */
    public static String getFormateTime(long time, String formate) {
        try {
            SimpleDateFormat formator = new SimpleDateFormat(formate);
            return formator.format(new Date(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "1970/12/21";

    }

    /**
     * 当前是否是圣诞节
     *
     * @return
     */
    public static boolean isChristmas() {
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        if (month == Calendar.DECEMBER && day >= 23 && day < 26) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是昨天
     *
     * @param time
     * @return
     */
    public static boolean isYesterday(long time) {

        Date oldTime = new Date(time);
        Date newTime = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String todayStr = format.format(newTime);
        Date today;
        try {
            today = format.parse(todayStr);
            if ((today.getTime() - oldTime.getTime()) > 0 && (today.getTime() - oldTime.getTime()) <= 86400000) {
                return true;
            } else if ((today.getTime() - oldTime.getTime()) <= 0) {
                return false;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 判断是否是今天
     *
     * @param time
     * @return
     */
    public static boolean isToday(long time) {

        Date oldTime = new Date(time);
        Date newTime = new Date(System.currentTimeMillis());
        return isSameDate(oldTime, newTime);
    }

    /**
     * 是否是同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
                && (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 聊天时显示的时间格式
     *
     * @param time
     * @return
     */
    public static String getTimeForChatMessage(long time) {

        if (isToday(time)) {
            return getFormateTime(time, "HH:mm");
        }

        if (isYesterday(time)) {
            return getFormateTime(time, "昨天 HH:mm");
        }

        return getFormateTime(time, "yyyy-MM-dd HH:mm");

    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();

        if (time2 >= time1) {
            long between_days = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(between_days));
        } else {
            return -1;
        }
    }

    /**
     * 获取当前时间前N天的0点时刻
     *
     * @param days
     * @return
     */
    public static long getPastDate0Time(int days) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        try {
            date = sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return System.currentTimeMillis();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        long time = cal.getTimeInMillis();
        return time - days * 24 * 60 * 60 * 1000;
    }

}
