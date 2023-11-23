package com.laoyancheng.www.controller;

import com.laoyancheng.www.db.domain.MarketLog;
import com.laoyancheng.www.service.MarketLogService;
import com.laoyancheng.www.service.impl.MarketLogServiceImpl;
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
 * @Description: 操作日志接口
 * @Author: JuRan
 * @Date: 2023/11/23 23:17
 */
@WebServlet("/admin/log/*")
public class AdminLogController extends HttpServlet {
    private MarketLogService marketLogService = new MarketLogServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/log/", "");
        if(StringUtils.equals("list", option)){
            listLog(req, resp);
        }
    }

    private void listLog(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer pageNum = Integer.valueOf(req.getParameter("page"));
        Integer pageSize = Integer.valueOf(req.getParameter("limit"));
        String sort = req.getParameter("sort");
        String order = req.getParameter("order");
        String name = req.getParameter("name");

        List<MarketLog> logList = marketLogService.list(pageNum, pageSize, sort, order, name);
        Object requestBody = ResponseUtil.okList(logList);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }
}
