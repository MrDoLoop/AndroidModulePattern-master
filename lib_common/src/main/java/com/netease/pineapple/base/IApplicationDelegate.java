package com.netease.pineapple.base;

import android.support.annotation.Keep;

/**
 * <p>类说明</p>
 *
 * @name ApplicationDelegate
 */
@Keep
public interface IApplicationDelegate {

    void onCreate();

    void onTerminate();

    void onLowMemory();

    void onTrimMemory(int level);

}
