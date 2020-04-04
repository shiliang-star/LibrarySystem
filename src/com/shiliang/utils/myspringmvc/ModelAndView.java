package com.shiliang.utils.myspringmvc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SL
 * @create 2020-03-20 13:47
 * @description ModelAndView 作用
 * 1. 设置转向地址
 * 2. 将底层获取的数据进行存储（或者封装）
 * 3. 最后将数据传递给页面
 */
public class ModelAndView {

    private String viewName;//跳转到的视图名称（路径）
    private Map<Object, Object> param = new HashMap<>();//存储数据


    public ModelAndView() {
    }

    public ModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<Object, Object> getParam() {
        return param;
    }

    public void addParam(Object key,Object value) {
        param.put(key, value);
    }

    public void setParam(Map<Object, Object> param) {
        this.param = param;
    }
}
