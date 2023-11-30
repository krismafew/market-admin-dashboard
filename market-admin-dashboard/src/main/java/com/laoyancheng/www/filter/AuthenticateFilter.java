package com.laoyancheng.www.filter;

import com.laoyancheng.www.db.domain.MarketAdmin;
import com.laoyancheng.www.util.JacksonUtil;
import com.laoyancheng.www.util.ResponseUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/30 16:50
 */
@WebFilter("/*")
public class AuthenticateFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)servletRequest;
        HttpServletResponse resp = (HttpServletResponse)servletResponse;

        String requestPath = req.getContextPath() + req.getRequestURI();
        ServletContext context = req.getServletContext();
        List<String> uriList = (List<String>)context.getAttribute("uriList");
        if(!uriList.contains(requestPath)){
            HttpSession session = req.getSession();
            Object info = session.getAttribute("info");
            if(info == null){
                resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.unauthz()));
                return;
            }
        }
        filterChain.doFilter(req, resp);

    }

    @Override
    public void destroy() {

    }
}
