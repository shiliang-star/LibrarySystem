package com.shiliang.utils.myspringmvc;

import com.shiliang.CheckBorrowInfo;
import com.shiliang.service.BorrowManageService;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

@WebListener()
public class ContextLoaderListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {
    // Public constructor is required by servlet spec
    public ContextLoaderListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */

//        Timer timer = new Timer();
//        TimerTask timerTask=new TimerTask() {
//            @Override
//            public void run() {
//              borrowManageService.checkBorrowInfo();
//            }
//        };
//        timer.schedule(timerTask, 0, 5000);
        try {
            //使用任务调度框架进行图书信息的检阅 判断是否有逾期信息
            System.out.println("定时器启动了");
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();

            //通过schedulerFactory对象获取任务调度器
            Scheduler scheduler = schedulerFactory.getScheduler();
            //创建一个Job
            JobDetail job = JobBuilder.newJob(CheckBorrowInfo.class)
                    .withIdentity("job1", "group1")
                    .build();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("CronTrigger1", "CronTriggerGroup")
                    //每天12点执行一次
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 12 * * ?"))
                    .startNow().build();
            //把job和触发器注册到调度器中
            scheduler.scheduleJob(job, trigger);
            //启动调度器
            scheduler.start();


            Set<Class<?>> classes = ScanClassUtil.getClasses("com.shiliang.controller");
            for (Class<?> clazz : classes) {
                //判断是否包含注解
                if (clazz.isAnnotationPresent(Controller.class)) {
                    //如果有注解那么取到注解的值，存入map集合中
                    Method[] methods = clazz.getMethods();
    //                System.out.println(methods.toString());
                    for (Method method : methods) {
                        //判断方法是否添加了注解
                        if (method.isAnnotationPresent(RequestMapping.class)) {
                            RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                            String url = annotation.value();
                            //URl不能重复
                            if (!RequestURLMap.urlmap.containsKey(url)) {
                                System.out.println(clazz.getName() + "类 中扫描到方法：" + method.getName() + " url 是：" + url);
                                RequestURLMap.urlmap.put(url, clazz);
                            }else {
                                System.out.println("重复 了");
                                throw new RuntimeException("在" +
                                        clazz.getName() + "类中发现，" + url + "有重复，请检查代码");
                            }

                        }
                    }
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
    }

    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

    public void attributeAdded(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute 
         is added to a session.
      */
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is removed from a session.
      */
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
      /* This method is invoked when an attribute
         is replaced in a session.
      */
    }
}
