package com.shiliang.utils.myspringmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author SL
 * @create 2020-03-20 19:08
 * @description 方便使用request 和 response（解决多线程安全问题）
 */
public class RequestContextHolder {

    //使用 ThreadLocal 维护变量时，ThreadLocal 为每个使用该变量的线程提供独立的变量副本，
    //所以每个线程都可以独立得改变自己的副本，从而不会影响到其它线程所对应的副本
    static ThreadLocal<HttpServletRequest> requestHolder=new ThreadLocal<>();

    static ThreadLocal<HttpServletResponse> responseHolder=new ThreadLocal<>();

    public static HttpServletRequest getRequest() {
        return requestHolder.get();
    }

    public static HttpServletResponse getResponse(){
        return responseHolder.get();
    }

    public static HttpSession getSession() {
        return requestHolder.get().getSession();
    }
}
