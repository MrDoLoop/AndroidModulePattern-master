package com.netease.pineapple.common.http;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class BaseStringObserver implements Observer<String> , ObserverStringCallback<String>{

    public BaseStringObserver() {

    }

    @Override
    final public void onSubscribe(Disposable d) {
        onRequestStart();
    }

    @Override
    final public void onNext(String value) {
        onRequestSuccess(value);
    }

    @Override
    final public void onError(Throwable e) {
        onRequestError(e.getLocalizedMessage(), e);
    }

    @Override
    final public void onComplete() {
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
}
