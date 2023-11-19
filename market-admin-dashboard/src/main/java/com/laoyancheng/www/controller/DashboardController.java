package com.laoyancheng.www.controller;

import com.laoyancheng.www.service.*;
import com.laoyancheng.www.service.impl.*;
import com.laoyancheng.www.util.JacksonUtil;
import com.laoyancheng.www.util.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;


@WebServlet("/admin/dashboard")
public class DashboardController extends HttpServlet {
    private MarketUserService marketUserService = new MarketUserServiceImpl();
    private MarketGoodsService marketGoodsService = new MarketGoodsServiceImpl();
    private MarketGoodsProductService marketGoodsProductService = new MarketGoodsProductServiceImpl();
    private MarketOrderService marketOrderService = new MarketOrderServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, Integer> data = new HashMap<>();
        data.put("goodsTotal", marketGoodsService.countGoods());
        data.put("userTotal", marketUserService.countUsers());
        data.put("productTotal", marketGoodsProductService.countProducts());
        data.put("orderTotal", marketOrderService.countOrders());
        Object responseBody = ResponseUtil.ok(data);
        resp.getWriter().println(JacksonUtil.writeValueAsString(responseBody));
    }
}
