package com.information.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class GsonUtils {

    public static final Gson sGson = new Gson();

    public static String toJson(Object o) {
        return sGson.toJson(o);
    }

    public static <T, E extends T> T fromJson(String s, Class<E> clazz) {
        Object o = sGson.fromJson(s, clazz);
        return (T) o;
    }

    /**
     * Type type = new TypeToken<List<PayMoney>>() {
     * }.getType();
     *
     * @param s
     * @param type
     * @param <T>
     * @param <E>
     * @return
     */
    public static <T, E extends T> T fromTypeJson(String s, Type type) {

        Object o = sGson.fromJson(s, type);
        return (T) o;
    }


    /**
     * Type type = new TypeToken<List<PayMoney>>() {
     * }.getType();
     *
     * @param s
     * @param <T>
     * @return
     */
    public static <T> T fromArrayTypeJson(String s) {
        Type type = new TypeToken<List<T>>() {
        }.getType();
        Object o = sGson.fromJson(s, type);
        return (T) o;
    }

    /**
     * Type type = new TypeToken<List<PayMoney>>() {
     * }.getType();
     *
     * @param s
     * @return
     */
    public static Map<String, Object> fromMapTypeJson(Object s) {
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        String json = null;
        if (s instanceof String) {
            json = (String) s;
        } else {
            json = toJson(s);
        }
        Object o = sGson.fromJson(json, type);
        return (Map<String, Object>) o;
    }

}
