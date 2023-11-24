package com.laoyancheng.www.controller;

import com.laoyancheng.www.db.domain.MarketUserGrowth;
import com.laoyancheng.www.service.MarketUserGrowthService;
import com.laoyancheng.www.service.impl.MarketUserGrowthServiceImpl;
import com.laoyancheng.www.util.JacksonUtil;
import com.laoyancheng.www.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description: 统计接口
 * @Author: JuRan
 * @Date: 2023/11/24 23:12
 */
@WebServlet("/admin/stat/*")
public class AdminStatController extends HttpServlet {
    private MarketUserGrowthService marketUserGrowthService = new MarketUserGrowthServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/stat/", "");
        if(StringUtils.equals("user", option)){
            getUserStat(req, resp);
        }else if(StringUtils.equals("goods", option)){
            getGoodsStat(req, resp);
        }
    }

    private void getGoodsStat(HttpServletRequest req, HttpServletResponse resp) {
        HashMap<String, Object> data = new HashMap<>();
        ArrayList<String> columns = new ArrayList<>();
        columns.add("day");
        columns.add("orders");
        columns.add("products");
        columns.add("amount");

        data.put("columns", columns);
    }

    private void getUserStat(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HashMap<String, Object> data = new HashMap<>();
        ArrayList<String> columns = new ArrayList<>();
        columns.add("day");
        columns.add("users");
        data.put("columns", columns);
        List<MarketUserGrowth> userGrowthList = marketUserGrowthService.list();
        data.put("rows", userGrowthList);
        Object requestBody = ResponseUtil.ok(data);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));

    }
}
