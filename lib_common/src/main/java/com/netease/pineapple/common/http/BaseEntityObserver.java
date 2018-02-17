package com.netease.pineapple.common.http;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * BaseEntityObserver
 * Created by jaycee on 2017/6/23.
 */
public abstract class BaseEntityObserver<T> implements Observer<BaseEntity<T>> , ObserverEntityCallback<T> {

    public BaseEntityObserver() {

    }

    @Override
    final public void onSubscribe(Disposable d) {
        onRequestStart();
    }

    @Override
    final public void onNext(BaseEntity<T> value) {
        if (value.isSuccess()) {
            onRequestSuccess(value);
        } else {
            onRequestError(value.getMsg(), new Throwable(value.getMsg()));
        }
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
    public void onRequestSuccess(BaseEntity<T> s) {

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
    public T onAboutToDeliverData(T t) {
        return t;
    }
}