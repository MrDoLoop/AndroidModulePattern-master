package com.netease.pineapple.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Gson处理工具类
 */
public class GsonUtil {

    public static String toJson(Object obj) {
        return new Gson().toJson(obj);
    }

    public static <T> T parse(String response, Type type) {
        T instance = null;
        try {
            instance = new Gson().fromJson(response, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (instance == null) {
            instance = getInstance(type);
        }
        return instance;
    }

    public static <T> T parse(String response, Class<T> clazz) {
        T instance = null;
        try {
            instance = new Gson().fromJson(response, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (instance == null) {
            instance = getInstance(clazz);
        }
        return instance;
    }

    private static <T> T getInstance(Type type) {
        T value = null;
        if (type instanceof Class) {
            value = getInstance((Class<? extends T>) type);
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            value = getInstance(parameterizedType.getRawType());
        }
        return value;
    }

    private static <T> T getInstance(Class<T> clazz) {
        if (List.class.equals(clazz)) {
            return (T) new ArrayList();
        } else if (Set.class.equals(clazz)) {
            return (T) new HashSet();
        } else if (Map.class.equals(clazz)) {
            return (T) new HashMap();
        }
        try {
            Constructor<T> constructor = clazz.getConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> jsonToList(String json, Class<T> clazz) {
        Type type = new TypeToken<ArrayList<JsonObject>>(){}.getType();
        List<JsonObject> jsonObjects = new Gson().fromJson(json, type);

        List<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects) {
            arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }

    public static <T> T parse(JsonElement response, Class<T> clazz, Gson gson) {
        T instance = null;
        try {
            if(response == null) return instance;

            if(gson != null) {
                instance = gson.fromJson(response, clazz);
            } else {
                instance = new Gson().fromJson(response, clazz);
            }
        } catch (Exception e) {
            //DebugLog.e(OkHttpClientUtils.class, "parse:" + response, e);
        }

        if (instance == null) {
            instance = getInstance(clazz);
        }
        return instance;
    }


    public static Gson getGsonWithDeserializer(Class beanClazz, JsonDeserializer deserializer) {
        GsonBuilder gsonb = new GsonBuilder();
        gsonb.registerTypeAdapter(beanClazz, deserializer);
        gsonb.serializeNulls();
        Gson gson = gsonb.create();
        return gson;
    }


}
