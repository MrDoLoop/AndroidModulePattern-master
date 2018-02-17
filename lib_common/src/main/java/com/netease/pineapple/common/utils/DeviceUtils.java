package com.netease.pineapple.common.utils;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import com.netease.galaxy.Galaxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by zhaonan on 2018/2/16.
 */

public class DeviceUtils {

    // 避免重复获取
    private static String DEV_ID = "";
    private static String DEV_MAC = "";

    /**
     * 判断当前Notification权限是否被禁止
     * 只支持4.4以上进行权限判断
     * @return
     */
    public static boolean systemNotificationPermissionEnable() {
        boolean enable;

        AppOpsManager mAppOps;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT) {
            // 4.4以下版本无法判断权限，默认返回通知权限开启，不做判断
            //NTLog.i(TAG, "system push permission ---------------- below 19 -------- always " + true);
            return true;
        }
        mAppOps = (AppOpsManager) PPUtils.getAppContext().getSystemService(Context.APP_OPS_SERVICE);

        ApplicationInfo appInfo = PPUtils.getAppContext().getApplicationInfo();
        int uid = appInfo.uid;

        Class appOpsClass; /* Context.APP_OPS_MANAGER */

        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField("OP_POST_NOTIFICATION");
            int value = (int) opPostNotificationValue.get(Integer.class);

            enable = (int) checkOpNoThrowMethod.invoke(mAppOps, value, uid, PPUtils.getAppContext().getPackageName()) == AppOpsManager.MODE_ALLOWED;
            LogUtils.i( "system push permission ---------------- check ------------- " + enable);
            return enable;

        } catch (Throwable e) {
            e.printStackTrace();
        }
        // 默认返回true
        LogUtils.i("system push permission ---------------- throwable ------------ always " + true);
        return true;
    }


    /**
     * 返回设备的DeviceId号
     *
     * @return 获取成功返回手机IMEI，否则返回null
     */

    public static String getDeviceId() {
        if(TextUtils.isEmpty(DEV_ID) || "000000000000000".equals(DEV_ID)) {
            return DEV_ID = Galaxy.getDeviceId(PPUtils.getAppContext());
        }
        return DEV_ID;
    }


    /**
     * 获取设备的Mac地址
     *
     * @return 获取成功返回Mac地址，否则返回null
     */
    public static String getMacAddress() {
        if(TextUtils.isEmpty(DEV_MAC)) {
            String macAddr = null;
            try {
                WifiManager wifi = (WifiManager) PPUtils.getAppContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = wifi.getConnectionInfo();
                macAddr = info.getMacAddress();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(macAddr)) {
                macAddr = "00:0A:00:00:00:00";
            }
            return macAddr;
        }
        return DEV_MAC;
    }

}
