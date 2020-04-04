package com.shiliang.filter;

import com.shiliang.domain.data.AuthorizationDo;
import com.shiliang.domain.vo.AdminDetailsVo;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static sun.misc.Version.print;

/**
 * @author 19655
 *        登陆效验
 */
@WebFilter(filterName = "AdminFilter", urlPatterns = "/admin/*")
public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

    /**
     * 判断是否登陆
     *   从session域中获取admin值
     *   若是空，未登陆
     *          跳转到登陆页面
     *    若不是空
     *         放行
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;


        String path = request.getRequestURI();

        AdminDetailsVo admin = (AdminDetailsVo) request.getSession().getAttribute("admin");

        String loginServletPath = "/admin/adminLogin.jsp";
        // 登陆页面无需过滤
        if (path.contains(loginServletPath)) {
            chain.doFilter(request, response);
            return;
        }

        //未登录就转发到登陆界面，登陆就放行
        if (admin == null) {
            response.sendRedirect(request.getContextPath() + "/admin/adminLogin.jsp");
            return;
        }



        //主页无须过滤
        if (path.contains("index.jsp")) {
            chain.doFilter(request, response);
            return;
        }
        AuthorizationDo authorization = admin.getAuthorization();

        //超级管理员无须过滤
        if (authorization.getSuperSet() == 1) {
            chain.doFilter(request, response);
            return;
        }

        /*
        管理员权限的过滤
        通过请求地址获取权限，判断此管理员是都有该权限
             有该权限
                   放行
             没有权限
                   跳转到nopass.jsp
         */
        boolean accessPermission = true;
        if (path.contains("readerManageController")&& authorization.getReaderSet()==1) {
            accessPermission = true;
        } else if (path.contains("readerTypeManageController") && authorization.getSysSet() == 1) {
            accessPermission =true;
        } else if (path.contains("bookManageController") && authorization.getBookSet() == 1) {
            accessPermission = true;
        } else if (path.contains("bookTypeManageController") && authorization.getTypeSet() == 1) {
            accessPermission = true;
        } else if (path.contains("borrowManageController") && authorization.getBorrowSet() == 1) {
            accessPermission = true;
        }else if (path.contains("backManageController") && authorization.getBackSet() == 1) {
            accessPermission = true;
        }else if (path.contains("borrowSearchController") && authorization.getBorrowSet() == 1) {
            accessPermission = true;
        }else if (path.contains("overDueManageController") && authorization.getForfeitSet() == 1) {
            accessPermission = true;
        } else {
            accessPermission = false;
        }
        if (accessPermission) {
            chain.doFilter(request, response);

        } else {

            response.sendRedirect(request.getContextPath() + "/error/nopass.jsp");
        }
//            return;
//        }

//        chain.doFilter(request, response);


        }

    @Override
    public void destroy() {
    }
}
