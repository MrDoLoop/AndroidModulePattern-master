package com.netease.pineapple.common.utils;

import java.util.List;

/**
 * Created by zhaonan on 2018/2/17.
 */

public class DataUtils {
    public static boolean listNotEmpty(List list) {
        if(list == null || list.isEmpty()) return false;
        return true;
    }
}
