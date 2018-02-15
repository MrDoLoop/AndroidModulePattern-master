package com.netease.pineapple;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.netease.pineapple.base.BaseApplication;
import com.netease.pineapple.utils.PPUtils;

public class PPApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        if (PPUtils.isAppDebug()) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // dex突破65535的限制
        MultiDex.install(this);
    }
}
