package com.shiliang.controller;

import com.shiliang.domain.data.AdminDo;
import com.shiliang.domain.vo.AdminDetailsVo;
import com.shiliang.service.AdminService;
import com.shiliang.utils.Md5Utils;
import com.shiliang.utils.myspringmvc.Controller;
import com.shiliang.utils.myspringmvc.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author SL
 * @create 2020-03-08 20:38
 * @description 管理员登陆
 */
@Controller
public class AdminLoginController extends BaseController {
    private AdminService service=new AdminService();

    @RequestMapping("/adminLoginController_login")
    public void login(HttpServletRequest request, HttpServletResponse response,String username,String password) throws IOException {
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
        AdminDo adminDo=new AdminDo();
        adminDo.setUsername(username);
        adminDo.setPassword(Md5Utils.md5(password));

        AdminDetailsVo admin = service.findAdmin(adminDo);


        int resultcode=1;

        //用户不存在 -1
        //用户密码错误 -2
        //登陆成功  1
        if (admin == null) {
//            System.out.println("用户名不存在");
            resultcode=-1;
//            response.getWriter().write("-1");
        } else if (!admin.getPassword().equals(adminDo.getPassword())) {
//            System.out.println("用户密码错误");
            resultcode=-2;
//            response.getWriter().write("-2");
        } else {
//            System.out.println("恭喜你登陆成功！");
            request.getSession().setAttribute("admin",admin);
        }
        response.getWriter().print(resultcode);


    }

    @RequestMapping("/adminLoginController_logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("admin");
        response.sendRedirect(request.getContextPath()+"/admin/adminLogin.jsp");
    }






}
