package com.shiliang.filter;

import com.shiliang.domain.data.ReaderDo;
import com.shiliang.domain.vo.AdminDetailsVo;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "ReaderFilter", urlPatterns = "/reader/*")
public class ReaderFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;


        String path = request.getRequestURI();

        ReaderDo reader = (ReaderDo) request.getSession().getAttribute("reader");

        String loginServletPath = "/reader/readerLogin.jsp";
        // 登陆页面无需过滤
        if (path.contains(loginServletPath)) {
            chain.doFilter(request, response);
            return;
        }

        //未登录就转发到登陆界面，登陆就放行
        if (reader == null) {
            response.sendRedirect(request.getContextPath() + "/reader/readerLogin.jsp");
            return;
        } else {
            chain.doFilter(request, response);
        }

    }

    public void destroy() {
    }
}
