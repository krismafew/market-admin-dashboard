package com.laoyancheng.www.controller;

import com.laoyancheng.www.constant.MarketOrderStatusConstants;
import com.laoyancheng.www.db.domain.MarketChannel;
import com.laoyancheng.www.db.domain.MarketOrder;
import com.laoyancheng.www.db.domain.MarketOrderGoods;
import com.laoyancheng.www.db.domain.MarketUser;
import com.laoyancheng.www.service.MarketChannelService;
import com.laoyancheng.www.service.MarketOrderGoodsService;
import com.laoyancheng.www.service.MarketOrderService;
import com.laoyancheng.www.service.MarketUserService;
import com.laoyancheng.www.service.impl.MarketChannelServiceImpl;
import com.laoyancheng.www.service.impl.MarketOrderGoodsServiceImpl;
import com.laoyancheng.www.service.impl.MarketOrderServiceImpl;
import com.laoyancheng.www.service.impl.MarketUserServiceImpl;
import com.laoyancheng.www.util.JacksonUtil;
import com.laoyancheng.www.util.ResponseUtil;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Pattern;
import javafx.util.converter.LocalDateTimeStringConverter;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @Description: 订单管理接口
 * @Author: JuRan
 * @Date: 2023/11/25 16:52
 */
@WebServlet("/admin/order/*")
public class AdminOrderController extends HttpServlet {
    private MarketOrderService marketOrderService = new MarketOrderServiceImpl();
    private MarketOrderGoodsService marketOrderGoodsService = new MarketOrderGoodsServiceImpl();
    private MarketUserService marketUserService = new MarketUserServiceImpl();
    private MarketChannelService marketChannelService = new MarketChannelServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/order/", "");
        if(StringUtils.equals("list", option)){
            listOrder(req, resp);
        }else if(StringUtils.equals("detail", option)){
            getOrder(req,resp);
        }else if(StringUtils.equals("channel", option)){
            listChannel(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/order/", "");
        if(StringUtils.equals("refund", option)){
            refund(req, resp);
        }else if(StringUtils.equals("ship", option)){
            ship(req, resp);
        }else if(StringUtils.equals("delete", option)){
            deleteOrder(req, resp);
        }
    }

    private void deleteOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletInputStream inputStream = req.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;

        while((len = inputStream.read(buffer)) != -1){
            byteArrayOutputStream.write(buffer, 0, len);
        }
        String jsonStr = byteArrayOutputStream.toString("utf-8");

        inputStream.close();
        byteArrayOutputStream.close();

        Integer orderId = Integer.valueOf(JacksonUtil.parseString(jsonStr, "orderId"));
        marketOrderService.delete(orderId);
        resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.ok()));
    }

    private void ship(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletInputStream inputStream = req.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;

        while((len = inputStream.read(buffer)) != -1){
            byteArrayOutputStream.write(buffer, 0, len);
        }
        String jsonStr = byteArrayOutputStream.toString("utf-8");

        inputStream.close();
        byteArrayOutputStream.close();

        Integer orderId = Integer.valueOf(JacksonUtil.parseString(jsonStr, "orderId"));
        String shipChannel = JacksonUtil.parseString(jsonStr, "shipChannel");
        String shipSn = JacksonUtil.parseString(jsonStr, "shipSn");

        if(StringUtils.isEmpty(shipChannel) || StringUtils.isEmpty(shipSn) || StringUtils.equals("null", shipChannel)){
            resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.badArgument()));
            return;
        }

        MarketOrder marketOrder = marketOrderService.selectOrderById(orderId);

        marketOrder.setOrderStatus(MarketOrderStatusConstants.SHIPPED);
        marketOrder.setShipChannel(shipChannel);
        marketOrder.setShipSn(shipSn);
        marketOrder.setShipTime(LocalDateTime.now());
        marketOrder.setUpdateTime(LocalDateTime.now());

        marketOrderService.update(marketOrder);
        resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.ok()));

    }

    private void listChannel(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<MarketChannel> channelList = marketChannelService.list();
        Object requestBody = ResponseUtil.ok(channelList);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }

    private void refund(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletInputStream inputStream = req.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;

        while((len = inputStream.read(buffer)) != -1){
            byteArrayOutputStream.write(buffer, 0, len);
        }
        String jsonStr = byteArrayOutputStream.toString("utf-8");

        inputStream.close();
        byteArrayOutputStream.close();

        Integer orderId = Integer.valueOf(JacksonUtil.parseString(jsonStr, "orderId"));
        BigDecimal refundMoney = new BigDecimal(JacksonUtil.parseString(jsonStr, "refundMoney"));

        MarketOrder marketOrder = marketOrderService.selectOrderById(orderId);
        marketOrder.setOrderStatus(MarketOrderStatusConstants.REFUND);
        marketOrder.setAftersaleStatus((short)3);
        marketOrder.setRefundAmount(refundMoney);
        marketOrder.setRefundTime(LocalDateTime.now());
        marketOrder.setUpdateTime(LocalDateTime.now());

        marketOrderService.update(marketOrder);
        resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.ok()));

    }

    private void getOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HashMap<String, Object> data = new HashMap<>();
        Integer orderId = Integer.valueOf(req.getParameter("id"));
        MarketOrder order = marketOrderService.selectOrderById(orderId);
        data.put("order", order);
        List<MarketOrderGoods> orderGoodsList = marketOrderGoodsService.selectOrderGoodsListByOrderId(orderId);
        data.put("orderGoods", orderGoodsList);
        MarketUser marketUser = marketUserService.selectOneById(order.getUserId());
        HashMap<String, Object> user = new HashMap<>();
        user.put("avatar", marketUser.getAvatar());
        user.put("nickname", marketUser.getNickname());
        data.put("user", user);
        Object requestBody = ResponseUtil.ok(data);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }

    private void listOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer pageNum = Integer.valueOf(req.getParameter("page"));
        Integer pageSize = Integer.valueOf(req.getParameter("limit"));
        String sort = req.getParameter("sort");
        String order = req.getParameter("order");
        String[] orderStatusArrays = req.getParameterValues("orderStatusArray");
        ArrayList<Short> statusList = new ArrayList<>();
        if(orderStatusArrays != null){
            for(String status : orderStatusArrays){
                statusList.add(Short.valueOf(status));
            }
        }
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startStr = req.getParameter("start");
        LocalDateTime start = null;
        if(!StringUtils.isEmpty(startStr)){
            start = parseStringToLocalDateTime(startStr, pattern);
        }

        String endStr = req.getParameter("end");
        LocalDateTime end = null;
        if(!StringUtils.isEmpty(endStr)){
            end = parseStringToLocalDateTime(endStr, pattern);
        }

        String userIdStr = req.getParameter("userId");
        Integer userId = null;
        if(!StringUtils.isEmpty(userIdStr))
            userId = Integer.valueOf(userIdStr);

        String orderSn = req.getParameter("orderSn");

        List<MarketOrder> orderList = marketOrderService.list(pageNum, pageSize, sort, order, userId, orderSn, start, end, statusList);
        Object requestBody = ResponseUtil.okList(orderList);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }

    private LocalDateTime parseStringToLocalDateTime(String startStr, DateTimeFormatter pattern) {
        return LocalDateTime.parse(startStr, pattern);
    }
}
