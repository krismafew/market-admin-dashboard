package com.laoyancheng.www.controller;

import com.laoyancheng.www.db.domain.MarketFeedback;
import com.laoyancheng.www.db.domain.MarketFootprint;
import com.laoyancheng.www.service.MarketFeedbackService;
import com.laoyancheng.www.service.impl.MarketFeedbackServiceImpl;
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
 * @Description: 用户反馈接口
 * @Author: JuRan
 * @Date: 2023/11/19 16:20
 */
@WebServlet("/admin/feedback/*")
public class AdminFeedbackController extends HttpServlet {
    private MarketFeedbackService marketFeedbackService = new MarketFeedbackServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/feedback/", "");
        if(StringUtils.equals("list", option)){
            listFeedback(req, resp);
        }
    }

    private void listFeedback(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer pageNum = Integer.valueOf(req.getParameter("page"));
        Integer pageSize = Integer.valueOf(req.getParameter("limit"));
        String sort = req.getParameter("sort");
        String order = req.getParameter("order");
        List<MarketFeedback> feedbackList = marketFeedbackService.list(pageNum, pageSize, sort, order);
        Object requestBody = ResponseUtil.okList(feedbackList);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }
}
