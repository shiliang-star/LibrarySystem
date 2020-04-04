package com.shiliang.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "CharacterEncodingFilter")
public class CharacterEncodingFilter implements Filter {
    String encode;
    public void init(FilterConfig config) throws ServletException {
        encode = config.getInitParameter("encode");
//        System.out.println(encode);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        if (encode != null) {
            req.setCharacterEncoding(encode);
            resp.setContentType("text/html;charset="+encode);
        }
        chain.doFilter(req, resp);
    }

    public void destroy() {
    }
}
