package com.netease.pineapple.common.http;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class BaseStringObserver implements Observer<String> , ObserverStringCallback<String>{

    public BaseStringObserver() {

    }

    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart();
    }

    @Override
    public void onNext(String value) {
        onRequestSuccess(value);
    }

    @Override
    public void onError(Throwable e) {
        onRequestError(e.getLocalizedMessage(), e);
    }

    @Override
    public void onComplete() {
        onRequestEnd();
    }

    // callback回调
    @Override
    public void onRequestSuccess(String s) {

    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void onRequestError(String msg, Throwable e) {

    }

    @Override
    public String onAboutToDeliverData(String s) {
        return s;
    }
}
