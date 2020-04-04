package com.shiliang.utils.myspringmvc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SL
 * @create 2020-02-28 15:39
 * @description 获取封装请求数据的Map集合
 */
public class RequestURLMap {
    public static Map<String, Class<?>> urlmap = new HashMap<>();

    /**
     * 判断URL是否存在
     */
    public static boolean isExist(String url) {
        return urlmap.containsKey(url);
    }
}
