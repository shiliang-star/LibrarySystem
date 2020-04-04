package com.shiliang.utils.myspringmvc;

import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @Description AnnotationHandleServlet 类的功能
 * 1. 对请求进行拦截
 * 2. 扫描添加了 MyWebServlet 注解的类，并一键值对形式存储起来
 * 3. 解析请求的 url
 * 4. 使用反射执行相应的方法
 * @ClassName AnnotationHandleServlet
 * @Author SL
 * @Date 2020/2/28 15:34
 */
public class DispatcherServlet extends HttpServlet {
//    @Override
//    public void init() throws ServletException {
//        super.init();
//        System.out.println("init.........");
//        Set<Class<?>> classes = ScanClassUtil.getClasses("com.shiliang");
//        for (Class<?> clazz : classes) {
//            //判断是否包含注解
//            if (clazz.isAnnotationPresent(Controller.class)) {
//                //如果有注解那么取到注解的值，存入map集合中
//                Method[] methods = clazz.getMethods();
//                System.out.println(methods.toString());
//                for (Method method : methods) {
//                    //判断方法是否添加了注解
//                    if (method.isAnnotationPresent(RequestMapping.class)) {
//                        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
//                        String url = annotation.value();
//                        //URl不能重复
//                        if (!RequestURLMap.urlmap.containsKey(url)) {
//                            System.out.println(clazz.getName() + "类 中扫描到方法：" + method.getName() + " url 是：" + url);
//                            RequestURLMap.urlmap.put(url, clazz);
//                        }else {
//                            System.out.println("重复 了");
//                            throw new RuntimeException("在" +
//                                    clazz.getName() + "类中发现，" + url + "有重复，请检查代码");
//                        }
//
//                    }
//                }
//            }
//        }
//    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //将当前线程的HttpServletRequest、HttpServletResponse对象存入ThreadLocal中，以方便在Controller中使用
        RequestContextHolder.requestHolder.set(request);
        RequestContextHolder.responseHolder.set(response);

        String lasturl = pareRequestURL(request);
//        System.out.println(url);
        Class<?> clazz = RequestURLMap.urlmap.get(lasturl);
        if (clazz == null) {
            throw new RuntimeException("找不到" + lasturl + "对应的类" + "请重新检查配置");
        }

        try {
            if (clazz.isAnnotationPresent(Controller.class)) {
                Method[] methods = clazz.getMethods();
                //实例化servle
                Object obj = clazz.newInstance();
                //记录目标方法
                // 请求路径和注解属性值相等，那么就是处理当前请求的方法
                Method m = null;
                for (Method method : methods) {
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        String url = method.getAnnotation(RequestMapping.class).value();
                        if (url != null && !"".equals(url.trim()) && lasturl.equals(url.trim())) {
                            //找到要执行的方法
                            m = method;
                            break;
                        }
                    }
                }
                if (m != null) {
                    System.out.println("获取到了执行的方法了：" + m.getName());
                    m.setAccessible(true);

                    //进行参数动态化选择
                    //获取方法的参数类型
                    Class<?>[] parameterTypes = m.getParameterTypes();

                    //获取方法的参数名称列表
                    List<String> methodParamNames = MethodUtils.getMethodParamNames(clazz, m);

                    Object[] os = new Object[parameterTypes.length];
                    for (int i = 0; i < parameterTypes.length; i++) {
                        Class c = parameterTypes[i];
                        if (c.equals(HttpServletRequest.class)) {
                            os[i] = request;
                        } else if (c.equals(HttpServletResponse.class)) {
                            os[i] = response;
                        } else if (c.equals(String.class) || c.equals(Integer.class) || c.equals(Double.class) || c.equals(Long.class) ||
                                c.equals(Boolean.class) || c.equals(int.class) || c.equals(double.class) || c.equals(long.class) || c.equals(boolean.class)) {

                            String param = request.getParameter(methodParamNames.get(i));
                            if (param != null) {
                                //参数的值不为空
                                //进行数据类型的间接性判断
                                try {
                                    if (c.equals(String.class)) {
                                        os[i] = param.toString();
                                    }else if (c.equals(Integer.class)) {
                                        os[i] = Integer.parseInt(param);
                                    }else if (c.equals(Double.class)) {
                                        os[i] = Double.parseDouble(param);
                                    }else if (c.equals(Long.class)) {
                                        os[i] = Long.parseLong(param);
                                    }else if (c.equals(Boolean.class)) {
                                        os[i] = Boolean.parseBoolean(param);
                                    }else if (c.equals(int.class)) {
                                        os[i] = Integer.parseInt(param);
                                    }else if (c.equals(double.class)) {
                                        os[i] = Double.parseDouble(param);
                                    }else if (c.equals(long.class)) {
                                        os[i] = Long.parseLong(param);
                                    }else if (c.equals(boolean.class)) {
                                        os[i] = Boolean.parseBoolean(param);
                                    }
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                    throw new RuntimeException(clazz.getName() + "类中的" + m.getName()
                                            + "方法参数【" + parameterTypes[i] + " " + methodParamNames.get(i) + "】与实际传入的值【" + param + "】"
                                            + "不匹配");
                                }
                            } else {
                                //如果参数是空的
                                //区分对象类型和基本类型
                                if (c.equals(String.class) || c.equals(Integer.class) || c.equals(Double.class)
                                        || c.equals(Long.class) || c.equals(Boolean.class)) {
                                    os[i] = null;
                                }
                                if (c.equals(int.class) || c.equals(double.class) || c.equals(long.class) ) {
                                    os[i] = 0;
                                }
                                if ( c.equals(boolean.class)) {
                                    os[i] = false;
                                }
                            }


                        } else {
                            //如果不是基本类型和常见的包装类，那么表示有可能是自定义的类型
                            //将请求的参数注入到该类中
                            Object o = c.newInstance();
                            BeanUtils.copyProperties(o, request.getParameterMap());
                            os[i] = o;
                        }
                    }

                    //获取返回值
                    Object result = m.invoke(obj, os);

                    //对返回值进行判断
                    if (result != null) {
                        if (result instanceof ModelAndView) {
                            ModelAndView modelAndView = (ModelAndView) result;

                            //获取存储的数据，并将其封装到域中
                            Map<Object, Object> params = modelAndView.getParam();
                            for (Object param : params.keySet()) {
                                request.setAttribute(param + "", params.get(param));
                            }

                            //获取到要访问的视图，并设置跳转页面
                            String viewName = modelAndView.getViewName();
                            if (viewName != null) {
                                request.getRequestDispatcher(viewName).forward(request, response);
                            } else {
                                throw new RuntimeException("你返回了ModelAndView,但是没有设置视图，请你调用setViewName()方法设置对应的视图");
                            }

                        } else {
                            throw new RuntimeException("无法解析返回值类型,请重新检查你的返回值类型");

                        }

                    }

                } else {
                    throw new RuntimeException("在类" + clazz.getSimpleName() + "找不到" + lasturl + "对应的方法，请检查方法是否存在！");
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }



    /**
     * 解析请求路径
     *
     * @param request
     * @return
     */
    private String pareRequestURL(HttpServletRequest request) {
        String path = request.getContextPath();
        String URI = request.getRequestURI();
        String laturl = URI.replaceFirst(path, "");
        laturl = laturl.substring(0, laturl.lastIndexOf("."));
        return laturl;
    }
}
