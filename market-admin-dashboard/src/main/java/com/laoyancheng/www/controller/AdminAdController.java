package com.laoyancheng.www.controller;

import com.laoyancheng.www.db.domain.MarketAd;
import com.laoyancheng.www.service.MarketAdService;
import com.laoyancheng.www.service.MarketCommentService;
import com.laoyancheng.www.service.impl.MarketAdServiceImpl;
import com.laoyancheng.www.service.impl.MarketCommentServiceImpl;
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
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description: 广告管理接口
 * @Author: JuRan
 * @Date: 2023/11/22 11:42
 */
@WebServlet("/admin/ad/*")
public class AdminAdController extends HttpServlet {
    private MarketAdService marketAdService = new MarketAdServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/ad/", "");
        if(StringUtils.equals("list", option)){
            listAd(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/ad/", "");
        if(StringUtils.equals("create", option)){
            createAd(req, resp);
        }else if(StringUtils.equals("update", option)){
            updateAd(req, resp);
        }else if(StringUtils.equals("delete", option)){
            deleteAd(req, resp);
        }
    }

    private void deleteAd(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

        marketAdService.delete(id);
        resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.ok()));
    }

    private void updateAd(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
        MarketAd marketAd = JacksonUtil.getObjectMapper().readValue(jsonStr, MarketAd.class);
        marketAd.setUpdateTime(LocalDateTime.now());

        marketAdService.update(marketAd);
        Object requestBody = ResponseUtil.ok(marketAd);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }

    private void createAd(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

        String content = JacksonUtil.parseString(jsonStr, "content");
        Boolean enabled = Boolean.valueOf(JacksonUtil.parseString(jsonStr, "enabled"));
        String link = JacksonUtil.parseString(jsonStr, "link");
        String name = JacksonUtil.parseString(jsonStr, "name");
        Byte position = Byte.valueOf(JacksonUtil.parseString(jsonStr, "position"));
        String url = JacksonUtil.parseString(jsonStr, "url");

        MarketAd marketAd = new MarketAd();
        marketAd.setContent(content);
        marketAd.setEnabled(enabled);
        marketAd.setLink(link);
        marketAd.setName(name);
        marketAd.setPosition(position);
        marketAd.setUrl(url);
        marketAd.setAddTime(LocalDateTime.now());
        marketAd.setUpdateTime(LocalDateTime.now());

        Object requestBody = ResponseUtil.ok(marketAdService.create(marketAd));
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }

    private void listAd(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer pageNum = Integer.valueOf(req.getParameter("page"));
        Integer pageSize = Integer.valueOf(req.getParameter("limit"));
        String sort = req.getParameter("sort");
        String order = req.getParameter("order");
        String name = req.getParameter("name");
        String content = req.getParameter("content");

        List<MarketAd> adList = marketAdService.list(pageNum, pageSize, sort, order, name, content);
        Object requestBody = ResponseUtil.okList(adList);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }
}
