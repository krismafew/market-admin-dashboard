package com.laoyancheng.www.controller;

import com.laoyancheng.www.db.domain.MarketGoods;
import com.laoyancheng.www.db.domain.MarketTopic;
import com.laoyancheng.www.service.MarketGoodsService;
import com.laoyancheng.www.service.MarketTopicService;
import com.laoyancheng.www.service.impl.MarketGoodsServiceImpl;
import com.laoyancheng.www.service.impl.MarketTopicServiceImpl;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description: 专题管理接口
 * @Author: JuRan
 * @Date: 2023/11/23 14:40
 */
@WebServlet("/admin/topic/*")
public class AdminTopicController extends HttpServlet {
    private MarketTopicService marketTopicService = new MarketTopicServiceImpl();
    private MarketGoodsService marketGoodsService = new MarketGoodsServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/topic/", "");
        if(StringUtils.equals("list", option)){
            listTopic(req, resp);
        }else if(StringUtils.equals("read", option)){
            listTopicById(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/topic/", "");
        if(StringUtils.equals("create", option)){
            createTopic(req, resp);
        }else if(StringUtils.equals("update", option)){
            updateTopic(req, resp);
        }else if(StringUtils.equals("delete", option)){
            deleteTopic(req, resp);
        }
    }

    private void deleteTopic(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

        Integer id = Integer.valueOf(JacksonUtil.parseString(jsonStr, "id"));
        marketTopicService.delete(id);
        resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.ok()));
    }

    private void updateTopic(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

        MarketTopic marketTopic = JacksonUtil.getObjectMapper().readValue(jsonStr, MarketTopic.class);
        marketTopic.setUpdateTime(LocalDateTime.now());
        marketTopicService.update(marketTopic);
        Object requestBody = ResponseUtil.ok(marketTopic);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }

    private void listTopicById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HashMap<String, Object> data = new HashMap<>();
        Integer id = Integer.valueOf(req.getParameter("id"));

        MarketTopic marketTopic = marketTopicService.selectOneById(id);
        Integer[] goodsIdList = marketTopic.getGoods();
        ArrayList<MarketGoods> goodsList = new ArrayList<>();
        for(Integer goodsId : goodsIdList){
            goodsList.add(marketGoodsService.selectGoodsById(goodsId));
        }
        data.put("goodsList", goodsList);
        data.put("topic", marketTopic);
        Object requestBody = ResponseUtil.ok(data);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }

    private void createTopic(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

        MarketTopic marketTopic = JacksonUtil.getObjectMapper().readValue(jsonStr, MarketTopic.class);
        marketTopic.setAddTime(LocalDateTime.now());
        marketTopic.setUpdateTime(LocalDateTime.now());

        Object requestBody = ResponseUtil.ok(marketTopicService.create(marketTopic));
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }

    private void listTopic(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer pageNum = Integer.valueOf(req.getParameter("page"));
        Integer pageSize = Integer.valueOf(req.getParameter("limit"));
        String sort = req.getParameter("sort");
        String order = req.getParameter("order");
        String title = req.getParameter("title");
        String subtitle = req.getParameter("subtitle");

        List<MarketTopic> topicList = marketTopicService.list(pageNum, pageSize, sort, order, title, subtitle);
        Object requestBody = ResponseUtil.okList(topicList);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }
}
