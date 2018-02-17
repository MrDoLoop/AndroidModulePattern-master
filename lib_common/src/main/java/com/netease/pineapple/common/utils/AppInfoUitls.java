package com.netease.pineapple.common.utils;

import android.content.pm.PackageManager;
import android.text.TextUtils;

/**
 * Created by zhaonan on 2018/2/16.
 */

public class AppInfoUitls {

    private static String sAPP_VER_NAME = "";
    private static int sAPP_VER_CODE = -1;
    private static String sAPP_NAME = "";
    private static String sCHANNEL = "";


    public static String getChannel() {
        if(TextUtils.isEmpty(sCHANNEL)) {
            sCHANNEL = "netease"; //赵楠的测试 还没有完成
        }
        return sCHANNEL;
    }
    /**
     * App 名称
     * @return
     */
    public static String getAppName() {
        if (TextUtils.isEmpty(sAPP_NAME)) {
            sAPP_NAME = PPUtils.getAppContext().getPackageManager().getApplicationLabel(PPUtils.getAppContext().getApplicationInfo()).toString();
        }
        return sAPP_NAME;
    }

    /**
     * App 版本名称
     * @return
     */
    public static String getAppVerName() {
        if(TextUtils.isEmpty(sAPP_VER_NAME)) {
            try {
                sAPP_VER_NAME = PPUtils.getAppContext().getPackageManager().getPackageInfo(PPUtils.getAppContext().getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                LogUtils.i(e.getMessage());
            }
        }
        return sAPP_VER_NAME;

    }
    /**
     * App 版本code
     * @return
     */
    public static int getAppVerCode() {
        if(sAPP_VER_CODE == -1) {
            try {
                sAPP_VER_CODE = PPUtils.getAppContext().getPackageManager().getPackageInfo(PPUtils.getAppContext().getPackageName(), 0).versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                LogUtils.i(e.getMessage());
            }
        }
        return sAPP_VER_CODE;
    }
}
