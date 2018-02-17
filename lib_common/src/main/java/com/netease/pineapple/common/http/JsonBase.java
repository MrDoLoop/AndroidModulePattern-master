package com.netease.pineapple.common.http;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by zhaonan on 2018/2/16.
 */

public class JsonBase implements Serializable, Cloneable {

    public String toJson() {
        return new Gson().toJson(this);
    }

}
