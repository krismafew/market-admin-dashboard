package com.laoyancheng.www.controller;

import com.laoyancheng.www.db.domain.MarketSystem;
import com.laoyancheng.www.service.MarketSystemService;
import com.laoyancheng.www.service.impl.MarketSystemServiceImpl;
import com.laoyancheng.www.util.JacksonUtil;
import com.laoyancheng.www.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * @Description: 配置接口
 * @Author: JuRan
 * @Date: 2023/11/24 14:32
 */
@WebServlet("/admin/config/*")
public class AdminConfigController extends HttpServlet {
    private MarketSystemService marketSystemService = new MarketSystemServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/config/", "");
        if(StringUtils.equals("express", option)){
            showExpressConfig(req, resp);
        }else if(StringUtils.equals("order", option)){
            showOrderConfig(req, resp);
        }else if(StringUtils.equals("wx", option)){
            showWxConfig(req, resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/config/", "");
        if(StringUtils.equals("express", option)){
            setExpressConfig(req, resp);
        }else if(StringUtils.equals("order", option)){
            setOrderConfig(req, resp);
        }else if(StringUtils.equals("wx", option)){
            setWxConfig(req, resp);
        }
    }

    private void setWxConfig(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

        String marketWxCatlogGoods = JacksonUtil.parseString(jsonStr, "market_wx_catlog_goods");
        String marketWxCatlogList = JacksonUtil.parseString(jsonStr, "market_wx_catlog_list");
        String marketWxIndexBrand = JacksonUtil.parseString(jsonStr, "market_wx_index_brand");
        String marketWxIndexHot = JacksonUtil.parseString(jsonStr, "market_wx_index_hot");
        String marketWxIndexNew = JacksonUtil.parseString(jsonStr, "market_wx_index_new");
        String marketWxIndexTopic = JacksonUtil.parseString(jsonStr, "market_wx_index_topic");
        String marketWxShare = JacksonUtil.parseString(jsonStr, "market_wx_share");

        MarketSystem marketSystem = new MarketSystem();
        marketSystem.setUpdateTime(LocalDateTime.now());
        marketSystem.setKeyName("market_wx_catlog_goods");
        marketSystem.setKeyValue(marketWxCatlogGoods);
        marketSystemService.updateConfig(marketSystem);
        marketSystem.setKeyName("market_wx_catlog_list");
        marketSystem.setKeyValue(marketWxCatlogList);
        marketSystemService.updateConfig(marketSystem);
        marketSystem.setKeyName("market_wx_index_brand");
        marketSystem.setKeyValue(marketWxIndexBrand);
        marketSystemService.updateConfig(marketSystem);
        marketSystem.setKeyName("market_wx_index_hot");
        marketSystem.setKeyValue(marketWxIndexHot);
        marketSystemService.updateConfig(marketSystem);
        marketSystem.setKeyName("market_wx_index_new");
        marketSystem.setKeyValue(marketWxIndexNew);
        marketSystemService.updateConfig(marketSystem);
        marketSystem.setKeyName("market_wx_index_topic");
        marketSystem.setKeyValue(marketWxIndexTopic);
        marketSystemService.updateConfig(marketSystem);
        marketSystem.setKeyName("market_wx_share");
        marketSystem.setKeyValue(marketWxShare);
        marketSystemService.updateConfig(marketSystem);
        resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.ok()));
    }

    private void showWxConfig(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        MarketSystem marketWxCatlogGoods = marketSystemService.getConfig("market_wx_catlog_goods");
        MarketSystem marketWxCatlogList = marketSystemService.getConfig("market_wx_catlog_list");
        MarketSystem marketWxIndexBrand = marketSystemService.getConfig("market_wx_index_brand");
        MarketSystem marketWxIndexHot = marketSystemService.getConfig("market_wx_index_hot");
        MarketSystem marketWxIndexNew = marketSystemService.getConfig("market_wx_index_new");
        MarketSystem marketWxIndexTopic = marketSystemService.getConfig("market_wx_index_topic");
        MarketSystem marketWxShare = marketSystemService.getConfig("market_wx_share");

        HashMap<String, Object> data = new HashMap<>();
        data.put("market_wx_catlog_goods", marketWxCatlogGoods.getKeyValue());
        data.put("market_wx_catlog_list", marketWxCatlogList.getKeyValue());
        data.put("market_wx_index_brand", marketWxIndexBrand.getKeyValue());
        data.put("market_wx_index_hot", marketWxIndexHot.getKeyValue());
        data.put("market_wx_index_new", marketWxIndexNew.getKeyValue());
        data.put("market_wx_index_topic", marketWxIndexTopic.getKeyValue());
        data.put("market_wx_share", marketWxShare.getKeyValue());

        Object requestBody = ResponseUtil.ok(data);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }

    private void showOrderConfig(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        MarketSystem marketOrderComment = marketSystemService.getConfig("market_order_comment");
        MarketSystem marketOrderUnconfirm = marketSystemService.getConfig("market_order_unconfirm");
        MarketSystem marketOrderUnpaid = marketSystemService.getConfig("market_order_unpaid");

        HashMap<String, Object> data = new HashMap<>();
        data.put("market_order_comment", marketOrderComment.getKeyValue());
        data.put("market_order_unconfirm", marketOrderUnconfirm.getKeyValue());
        data.put("market_order_unpaid", marketOrderUnpaid.getKeyValue());
        Object requestBody = ResponseUtil.ok(data);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }

    private void setOrderConfig(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

        String marketOrderComment = JacksonUtil.parseString(jsonStr, "market_order_comment");
        String marketOrderUnconfirm = JacksonUtil.parseString(jsonStr, "market_order_unconfirm");
        String marketOrderUnpaid = JacksonUtil.parseString(jsonStr, "market_order_unpaid");
        MarketSystem marketSystem = new MarketSystem();
        marketSystem.setUpdateTime(LocalDateTime.now());
        marketSystem.setKeyName("market_order_comment");
        marketSystem.setKeyValue(marketOrderComment);
        marketSystemService.updateConfig(marketSystem);
        marketSystem.setKeyName("market_order_unconfirm");
        marketSystem.setKeyValue(marketOrderUnconfirm);
        marketSystemService.updateConfig(marketSystem);
        marketSystem.setKeyName("market_order_unpaid");
        marketSystem.setKeyValue(marketOrderUnpaid);
        marketSystemService.updateConfig(marketSystem);

        resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.ok()));
    }

    private void setExpressConfig(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

        String marketExpressFreightMin = JacksonUtil.parseString(jsonStr, "market_express_freight_min");
        String market_express_freight_value = JacksonUtil.parseString(jsonStr, "market_express_freight_value");
        MarketSystem marketSystem = new MarketSystem();
        marketSystem.setUpdateTime(LocalDateTime.now());
        marketSystem.setKeyName("market_express_freight_min");
        marketSystem.setKeyValue(marketExpressFreightMin);
        marketSystemService.updateConfig(marketSystem);
        marketSystem.setKeyName("market_express_freight_value");
        marketSystem.setKeyValue(market_express_freight_value);
        marketSystemService.updateConfig(marketSystem);
        resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.ok()));
    }

    private void showExpressConfig(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        MarketSystem freightMin = marketSystemService.getConfig("market_express_freight_min");
        MarketSystem freightValue = marketSystemService.getConfig("market_express_freight_value");
        HashMap<String, Object> data = new HashMap<>();
        data.put("market_express_freight_min", freightMin.getKeyValue());
        data.put("market_express_freight_value", freightValue.getKeyValue());
        Object requestBody = ResponseUtil.ok(data);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }
}
