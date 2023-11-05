package com.laoyancheng.www.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class CORSFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 首先将servletRequest向下转型为HttpServletRequest, servletResponse向下转型为HttpServletResponse
        HttpServletRequest req = (HttpServletRequest)servletRequest;
        HttpServletResponse resp = (HttpServletResponse)servletResponse;
        // 解决跨域问题
        // 给resp设置若干个响应头

        // 告诉浏览器，只有来自http://localhost:9527的请求被允许访问此资源
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:9527");
        // 告诉浏览器，允许的HTTP方法有POST、GET、OPTIONS、PUT和DELETE。
        resp.setHeader("Access-Control-Allow-Method", "POST,GET,OPTIONS,PUT,DELETE");
        // 告诉浏览器，哪些HTTP头可以在跨域请求中使用
        resp.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,Content-Type,X-CskaoyanMarket-Admin-Token," +
                "X-CskaoyanMarket-Token");
        // 告诉浏览器，服务器允许浏览器在请求中包括任何预先设置的凭据，如cookies、HTTP认证或客户端SSL证书。
        resp.setHeader("Access-Control-Allow-Credentials", "true");

        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
