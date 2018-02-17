package com.netease.pineapple.common.http;

/**
 * Created by ZHAO_NAN on 2018/1/20.
 */

public interface ObserverStringCallback<T> {
    void onRequestStart();

    void onRequestSuccess(T t);

    void onRequestError(String msg, Throwable e);

    void onRequestEnd();
}
