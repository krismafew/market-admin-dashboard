package com.laoyancheng.www.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class CharacterEncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)servletRequest;
        HttpServletResponse resp = (HttpServletResponse)servletResponse;

        if(!req.getRequestURI().startsWith("admin/storage/fetch/")) {
            resp.setContentType("text/html;charset=utf-8");
            req.setCharacterEncoding("utf-8");
        }
        filterChain.doFilter(req, resp);

    }

    @Override
    public void destroy() {

    }
}
