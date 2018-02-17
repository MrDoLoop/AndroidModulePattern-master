package com.netease.pineapple.common.http;

public class BaseEntity<E> extends JsonBase {

    private static final long serialVersionUID = 1L;

    public int code;

    public String message;

    public E data;

    public boolean isSuccess() {
        return code == 200;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return message;
    }

    public E getData() {
        return data;
    }
}
