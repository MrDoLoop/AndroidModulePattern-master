package com.netease.pineapple.common.http;

/**
 * Created by ZHAO_NAN on 2018/1/20.
 */

public interface ObserverStringCallback<T> {
    void onRequestStart();

    /**
     * 该方法在子线程中调用 方便在返回数据前进行修改
     * @param t
     * @return
     */
    T onAboutToDeliverData(T t);

    void onRequestSuccess(T t);

    void onRequestError(String msg, Throwable e);

    void onRequestEnd();
}
