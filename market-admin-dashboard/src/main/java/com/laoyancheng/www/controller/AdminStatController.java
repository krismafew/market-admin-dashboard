package com.laoyancheng.www.controller;

import com.laoyancheng.www.db.domain.MarketOrderStat;
import com.laoyancheng.www.db.domain.MarketUserGrowth;
import com.laoyancheng.www.service.MarketOrderStatService;
import com.laoyancheng.www.service.MarketUserGrowthService;
import com.laoyancheng.www.service.impl.MarketOrderStatServiceImpl;
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
import java.util.Arrays;
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
    private MarketOrderStatService marketOrderStatService = new MarketOrderStatServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/stat/", "");
        if(StringUtils.equals("user", option)){
            getUserStat(req, resp);
        }else if(StringUtils.equals("goods", option)){
            getGoodsStat(req, resp);
        }else if(StringUtils.equals("order", option)) {
            getOrderStat(req, resp);
        }
    }

    private void getOrderStat(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HashMap<String, Object> data = new HashMap<>();
        data.put("columns", Arrays.asList(new String[]{"day", "orders", "customers", "amount", "pcr"}));
        List<MarketOrderStat> orderStatList = marketOrderStatService.list();
        data.put("rows", orderStatList);
        Object requestBody = ResponseUtil.ok(data);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }

    private void getGoodsStat(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HashMap<String, Object> data = new HashMap<>();
        data.put("columns", Arrays.asList(new String[]{"day", "orders", "products", "amount"}));
        List<MarketOrderStat> orderStatList = marketOrderStatService.list();
        data.put("rows", orderStatList);
        Object requestBody = ResponseUtil.ok(data);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }

    private void getUserStat(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HashMap<String, Object> data = new HashMap<>();
        data.put("columns", new String[]{"day", "users"});
        List<MarketUserGrowth> userGrowthList = marketUserGrowthService.list();
        data.put("rows", userGrowthList);
        Object requestBody = ResponseUtil.ok(data);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));

    }
}
