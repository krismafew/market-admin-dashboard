package com.laoyancheng.www.controller;

import com.github.pagehelper.Page;
import com.laoyancheng.www.db.domain.MarketUser;
import com.laoyancheng.www.service.MarketUserService;
import com.laoyancheng.www.service.impl.MarketUserServiceImpl;
import com.laoyancheng.www.util.JacksonUtil;
import com.laoyancheng.www.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Description: 用户管理接口
 * @Author: JuRan
 * @Date: 2023/11/18 23:21
 */
@WebServlet("/admin/user/*")
public class AdminUserController extends HttpServlet {
    private MarketUserService marketUserService = new MarketUserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/", "").replace("/list", "");

        if(StringUtils.equals(option, "user")){
            listUsers(req, resp);
        }
    }

    private void listUsers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer pageNum = Integer.valueOf(req.getParameter("page"));
        Integer pageSize = Integer.valueOf(req.getParameter("limit"));
        String sort = req.getParameter("sort");
        String order = req.getParameter("order");
        List<MarketUser> userList = marketUserService.list(pageNum, pageSize, sort, order);
        Object requestBody = ResponseUtil.okList(userList);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }
}
