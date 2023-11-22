package com.laoyancheng.www.controller;

import com.laoyancheng.www.db.DTO.MarketCategoryDTO;
import com.laoyancheng.www.db.DTO.MarketCategoryLabelDTO;
import com.laoyancheng.www.db.domain.MarketCategory;
import com.laoyancheng.www.service.MarketCategoryService;
import com.laoyancheng.www.service.impl.MarketCategoryServiceImpl;
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
 * @Description: 商品类目管理接口
 * @Author: JuRan
 * @Date: 2023/11/20 21:51
 */
@WebServlet("/admin/category/*")
public class AdminCategoryController extends HttpServlet {
    private MarketCategoryService marketCategoryService = new MarketCategoryServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/category/", "");
        if(StringUtils.equals("list", option)){
            listCategory(req, resp);
        }else if(StringUtils.equals("l1", option)){
            listCategoryL1(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/category/", "");
        if(StringUtils.equals("create", option)){
            createCategory(req, resp);
        }else if(StringUtils.equals("update", option)){
            updateCategory(req, resp);
        }else if(StringUtils.equals("delete", option)){
            deleteCategory(req, resp);
        }
    }

    private void listCategoryL1(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<MarketCategoryLabelDTO> categoryL1DTOList = marketCategoryService.listL1();
        Object requestBody = ResponseUtil.okList(categoryL1DTOList);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }

    private void deleteCategory(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
        marketCategoryService.delete(id);
        resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.ok()));
    }

    private void updateCategory(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
        String description = JacksonUtil.parseString(jsonStr, "desc");
        String iconUrl = JacksonUtil.parseString(jsonStr, "iconUrl");
        Integer id = Integer.valueOf(JacksonUtil.parseString(jsonStr, "id"));
        String keywords = JacksonUtil.parseString(jsonStr, "keywords");
        String level = JacksonUtil.parseString(jsonStr, "level");
        String name = JacksonUtil.parseString(jsonStr, "name");
        String picUrl = JacksonUtil.parseString(jsonStr, "picUrl");
        Integer pid = null;
        try {
            pid = Integer.valueOf(JacksonUtil.parseString(jsonStr, "pid"));
        } catch (NumberFormatException e) {
            resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.badArgument()));
            return;
        }

        // 封装marketCategory
        MarketCategory marketCategory = new MarketCategory();
        if(description == null)
            marketCategory.setDesc("");
        else
            marketCategory.setDesc(description);
        marketCategory.setIconUrl(iconUrl);
        marketCategory.setId(id);
        if(keywords == null)
            marketCategory.setKeywords("");
        else
            marketCategory.setKeywords(keywords);
        marketCategory.setLevel(level);
        marketCategory.setName(name);
        marketCategory.setName(name);
        marketCategory.setPicUrl(picUrl);
        marketCategory.setPid(pid);

        marketCategory.setUpdateTime(LocalDateTime.now());

        marketCategoryService.update(marketCategory);
        resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.ok()));

    }

    private void createCategory(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
        String description = JacksonUtil.parseString(jsonStr, "desc");
        String iconUrl = JacksonUtil.parseString(jsonStr, "iconUrl");
        String keywords = JacksonUtil.parseString(jsonStr, "keywords");
        String level = JacksonUtil.parseString(jsonStr, "level");
        String name = JacksonUtil.parseString(jsonStr, "name");
        String picUrl = JacksonUtil.parseString(jsonStr, "picUrl");
        Integer pid = Integer.valueOf(JacksonUtil.parseString(jsonStr, "pid"));

        // 封装到MarketCategory对象中去
        MarketCategory marketCategory = new MarketCategory();
        marketCategory.setDesc(description);
        marketCategory.setIconUrl(iconUrl);
        if(keywords == null)
            marketCategory.setKeywords("");
        else
            marketCategory.setKeywords(keywords);
        marketCategory.setLevel(level);
        marketCategory.setName(name);
        marketCategory.setName(name);
        marketCategory.setPicUrl(picUrl);
        marketCategory.setPid(pid);

        marketCategory.setAddTime(LocalDateTime.now());
        marketCategory.setUpdateTime(LocalDateTime.now());

        Object requestBody = ResponseUtil.ok(marketCategoryService.create(marketCategory));
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));


    }

    private void listCategory(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<MarketCategoryDTO> list = marketCategoryService.list();
        Object responseBody = ResponseUtil.okList(list);
        resp.getWriter().println(JacksonUtil.writeValueAsString(responseBody));
    }
}
