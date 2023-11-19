package com.laoyancheng.www.controller;

import com.laoyancheng.www.db.domain.MarketFootprint;
import com.laoyancheng.www.db.domain.MarketUser;
import com.laoyancheng.www.service.MarketFootprintService;
import com.laoyancheng.www.service.impl.MarketFootprintServiceImpl;
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
 * @Description: 用户足迹接口
 * @Author: JuRan
 * @Date: 2023/11/19 15:39
 */
@WebServlet("/admin/footprint/*")
public class AdminFootprintController extends HttpServlet {
    private MarketFootprintService marketFootprintService = new MarketFootprintServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/footprint/", "");
        if(StringUtils.equals("list", option)){
            listFootprint(req, resp);
        }
    }

    private void listFootprint(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer pageNum = Integer.valueOf(req.getParameter("page"));
        Integer pageSize = Integer.valueOf(req.getParameter("limit"));
        String sort = req.getParameter("sort");
        String order = req.getParameter("order");
        List<MarketFootprint> footprintList = marketFootprintService.list(pageNum, pageSize, sort, order);
        Object requestBody = ResponseUtil.okList(footprintList);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }
}
