package com.laoyancheng.www.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.laoyancheng.www.db.domain.MarketAddress;
import com.laoyancheng.www.db.domain.MarketUser;
import com.laoyancheng.www.service.MarketAddressService;
import com.laoyancheng.www.service.impl.MarketAddressServiceImpl;
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
 * @Description: 收货地址管理接口
 * @Author: JuRan
 * @Date: 2023/11/19 13:33
 */
@WebServlet("/admin/address/*")
public class AdminAddressController extends HttpServlet {
    private MarketAddressService marketAddressService = new MarketAddressServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/address/", "");
        if(StringUtils.equals("list", option)){
            listAddress(req, resp);
        }
    }

    private void listAddress(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer pageNum = Integer.valueOf(req.getParameter("page"));
        Integer pageSize = Integer.valueOf(req.getParameter("limit"));
        String sort = req.getParameter("sort");
        String order = req.getParameter("order");
        String name = req.getParameter("name");
        String userIdStr = req.getParameter("userId");
        Integer userId = null;
        if(!StringUtils.isEmpty(userIdStr))
            userId = Integer.valueOf(userIdStr);
        List<MarketAddress> addressList = marketAddressService.list(pageNum, pageSize, sort, order, name, userId);
        Object requestBody = ResponseUtil.okList(addressList);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }
}
