package com.laoyancheng.www.controller;

import com.laoyancheng.www.db.domain.MarketKeyword;
import com.laoyancheng.www.service.MarketKeywordService;
import com.laoyancheng.www.service.impl.MarketKeywordServiceImpl;
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
import java.util.List;

/**
 * @Description: 关键词接口
 * @Author: JuRan
 * @Date: 2023/11/21 20:40
 */
@WebServlet("/admin/keyword/*")
public class AdminKeywordController extends HttpServlet {
    private MarketKeywordService marketKeywordService = new MarketKeywordServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/keyword/", "");
        if(StringUtils.equals("list", option)){
            listKeyword(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/keyword/", "");
        if(StringUtils.equals("create", option)){
            createKeyword(req, resp);
        }else if(StringUtils.equals("update", option)){
            updateKeyword(req, resp);
        }else if(StringUtils.equals("delete", option)){
            deleteKeyword(req, resp);
        }
    }

    private void deleteKeyword(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

        // 解析Json字符串
        Integer id = Integer.valueOf(JacksonUtil.parseString(jsonStr, "id"));

        marketKeywordService.delete(id);
        resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.ok()));
    }

    private void updateKeyword(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

        // 解析Json字符串
        Integer id = Integer.valueOf(JacksonUtil.parseString(jsonStr, "id"));
        Boolean isDefault = Boolean.valueOf(JacksonUtil.parseString(jsonStr, "isDefault"));
        Boolean isHot = Boolean.valueOf(JacksonUtil.parseString(jsonStr, "isHot"));
        String keyword = JacksonUtil.parseString(jsonStr, "keyword");
        String url = JacksonUtil.parseString(jsonStr, "url");
        // 封装对象
        MarketKeyword marketKeyword = new MarketKeyword();
        marketKeyword.setId(id);
        marketKeyword.setIsDefault(isDefault);
        marketKeyword.setIsHot(isHot);
        marketKeyword.setKeyword(keyword);
        marketKeyword.setUrl(url);
        marketKeyword.setUpdateTime(LocalDateTime.now());

        marketKeywordService.update(marketKeyword);
        Object requestBody = ResponseUtil.ok(marketKeyword);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }

    private void createKeyword(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

        // 解析Json字符串
        Boolean isDefault = Boolean.valueOf(JacksonUtil.parseString(jsonStr, "isDefault"));
        Boolean isHot = Boolean.valueOf(JacksonUtil.parseString(jsonStr, "isHot"));
        String keyword = JacksonUtil.parseString(jsonStr, "keyword");
        String url = JacksonUtil.parseString(jsonStr, "url");

        MarketKeyword marketKeyword = new MarketKeyword();
        marketKeyword.setIsDefault(isDefault);
        marketKeyword.setIsHot(isHot);
        marketKeyword.setKeyword(keyword);
        marketKeyword.setUrl(url);
        marketKeyword.setAddTime(LocalDateTime.now());
        marketKeyword.setUpdateTime(LocalDateTime.now());

        Object requestBody = ResponseUtil.ok(marketKeywordService.create(marketKeyword));
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }

    private void listKeyword(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer pageNum = Integer.valueOf(req.getParameter("page"));
        Integer pageSize = Integer.valueOf(req.getParameter("limit"));
        String sort = req.getParameter("sort");
        String order = req.getParameter("order");

        List<MarketKeyword> keywordList = marketKeywordService.list(pageNum, pageSize, sort, order);
        Object requestBody = ResponseUtil.okList(keywordList);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }
}
