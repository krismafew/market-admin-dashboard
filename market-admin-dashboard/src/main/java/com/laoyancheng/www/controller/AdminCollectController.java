package com.laoyancheng.www.controller;

import com.laoyancheng.www.db.domain.MarketAddress;
import com.laoyancheng.www.db.domain.MarketCollect;
import com.laoyancheng.www.service.MarketCollectService;
import com.laoyancheng.www.service.impl.MarketCollectServiceImpl;
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
 * @Description: 用户收藏接口
 * @Author: JuRan
 * @Date: 2023/11/19 15:02
 */
@WebServlet("/admin/collect/*")
public class AdminCollectController extends HttpServlet {
    private MarketCollectService marketCollectService = new MarketCollectServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/collect/", "");
        if(StringUtils.equals("list", option)){
            listCollect(req, resp);
        }
    }

    private void listCollect(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer pageNum = Integer.valueOf(req.getParameter("page"));
        Integer pageSize = Integer.valueOf(req.getParameter("limit"));
        String sort = req.getParameter("sort");
        String order = req.getParameter("order");
        List<MarketCollect> collectList = marketCollectService.list(pageNum, pageSize, sort, order);
        Object requestBody = ResponseUtil.okList(collectList);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }
}
