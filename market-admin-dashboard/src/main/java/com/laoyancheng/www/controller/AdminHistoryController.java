package com.laoyancheng.www.controller;

import com.laoyancheng.www.db.domain.MarketCollect;
import com.laoyancheng.www.db.domain.MarketSearchHistory;
import com.laoyancheng.www.service.MarketSearchHistoryService;
import com.laoyancheng.www.service.impl.MarketSearchHistoryServiceImpl;
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
 * @Description: 用户搜索历史接口
 * @Author: JuRan
 * @Date: 2023/11/19 16:02
 */
@WebServlet("/admin/history/*")
public class AdminHistoryController extends HttpServlet {
    private MarketSearchHistoryService marketSearchHistoryService = new MarketSearchHistoryServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/history/", "");
        if(StringUtils.equals("list", option)){
            listHistory(req, resp);
        }
    }

    private void listHistory(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer pageNum = Integer.valueOf(req.getParameter("page"));
        Integer pageSize = Integer.valueOf(req.getParameter("limit"));
        String sort = req.getParameter("sort");
        String order = req.getParameter("order");
        List<MarketSearchHistory> historyList = marketSearchHistoryService.list(pageNum, pageSize, sort, order);
        Object requestBody = ResponseUtil.okList(historyList);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }
}
