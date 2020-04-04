package com.shiliang.controller;

import com.shiliang.domain.data.AdminDo;
import com.shiliang.utils.myspringmvc.ModelAndView;
import com.shiliang.utils.myspringmvc.Controller;
import com.shiliang.utils.myspringmvc.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author SL
 * @create 2020-03-18 16:58
 * @description 测试Controller
 */
@Controller
public class TestController {

    @RequestMapping("/t1")
    public void t1( HttpServletResponse response,HttpServletRequest request) {

        System.out.println("t1被调用了！");
    }


    @RequestMapping("/t2")
    public String t2() {
        System.out.println("t2被调用了");
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addParam("msg", "我是Controller传过来的数据");
        return "我是返回值";
    }


    @RequestMapping("/t3")
    public ModelAndView t3() {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("/test.jsp");
        modelAndView.addParam("msg", "我是Controller传过来的数据");
        modelAndView.addParam("msg2", "我是第二个传过来的数据");
        return modelAndView;

    }


    @RequestMapping("/t4")
    public void t4(AdminDo adminDo) {

        System.out.println(adminDo.getUsername()+" "+adminDo.getPassword());

    }





}
