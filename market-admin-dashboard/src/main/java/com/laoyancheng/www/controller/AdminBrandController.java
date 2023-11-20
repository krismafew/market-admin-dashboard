package com.laoyancheng.www.controller;

import com.laoyancheng.www.db.domain.MarketBrand;
import com.laoyancheng.www.db.domain.MarketCollect;
import com.laoyancheng.www.service.MarketBrandService;
import com.laoyancheng.www.service.impl.MarketBrandServiceImpl;
import com.laoyancheng.www.util.JacksonUtil;
import com.laoyancheng.www.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @Description: 品牌管理接口
 * @Author: JuRan
 * @Date: 2023/11/19 16:42
 */
@WebServlet("/admin/brand/*")
public class AdminBrandController extends HttpServlet {
    private MarketBrandService marketBrandService = new MarketBrandServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/brand/", "");
        if(StringUtils.equals("list", option)){
            listBrand(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/brand/", "");
        if(StringUtils.equals("create", option)){
            createBrand(req, resp);
        }else if(StringUtils.equals("update", option)){
            updateBrand(req, resp);
        }else if(StringUtils.equals("delete", option)){
            deleteBrand(req, resp);
        }
    }

    private void deleteBrand(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletInputStream inputStream = req.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len = 0;
        while((len = inputStream.read(bytes)) != -1){
            byteArrayOutputStream.write(bytes, 0, len);
        }

        String jsonStr = byteArrayOutputStream.toString("utf-8");
        inputStream.close();
        byteArrayOutputStream.close();

        Integer id = Integer.valueOf(JacksonUtil.parseString(jsonStr, "id"));
        MarketBrand marketBrand = new MarketBrand();
        marketBrand.setId(id);

        marketBrandService.delete(id);
        resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.ok()));
    }

    private void updateBrand(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletInputStream inputStream = req.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len = 0;
        while((len = inputStream.read(bytes)) != -1){
            byteArrayOutputStream.write(bytes, 0, len);
        }

        String jsonStr = byteArrayOutputStream.toString("utf-8");
        inputStream.close();
        byteArrayOutputStream.close();

        String description = JacksonUtil.parseString(jsonStr, "desc");
        String floorPriceStr = JacksonUtil.parseString(jsonStr, "floorPrice");
        String name = JacksonUtil.parseString(jsonStr, "name");
        String picUrl = JacksonUtil.parseString(jsonStr, "picUrl");
        Integer id = Integer.valueOf(JacksonUtil.parseString(jsonStr, "id"));

        if(StringUtils.isEmpty(description) || StringUtils.isEmpty(floorPriceStr)){
            resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.badArgument()));
            return;
        }
        BigDecimal floorPrice;
        try {
            floorPrice = new BigDecimal(floorPriceStr);
        } catch (NumberFormatException e) {
            resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.badArgumentValue()));
            return;
        }

        MarketBrand marketBrand = new MarketBrand();
        marketBrand.setId(id);
        marketBrand.setName(name);
        marketBrand.setFloorPrice(floorPrice);
        marketBrand.setPicUrl(picUrl);
        marketBrand.setDesc(description);
        marketBrand.setUpdateTime(LocalDateTime.now());

        Object requestBody = ResponseUtil.ok(marketBrandService.update(marketBrand));
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));


    }

    private void createBrand(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 获取参数
        ServletInputStream inputStream = req.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len = 0;

        while((len = inputStream.read(bytes)) != -1){
            byteArrayOutputStream.write(bytes);
        }
        String jsonString = byteArrayOutputStream.toString("utf-8");
        inputStream.close();
        byteArrayOutputStream.close();

        String description = JacksonUtil.parseString(jsonString, "desc");

        // 将String类型转化为BigDecimal类型
        String floorPriceStr = JacksonUtil.parseString(jsonString, "floorPrice");
        String name = JacksonUtil.parseString(jsonString, "name");
        String picUrl = JacksonUtil.parseString(jsonString, "picUrl");
        // 如果description, name, floorPrice为null, 返回参数不正确的状态码
        if(StringUtils.isEmpty(floorPriceStr) || StringUtils.isEmpty(name) || StringUtils.isEmpty(description)){
            resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.badArgument()));
            return;
        }
        // 判断floorPrice是否为数字
        BigDecimal floorPrice ;
        try {
            floorPrice = new BigDecimal(floorPriceStr);
        } catch (Exception e) {
            resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.badArgumentValue()));
            return;
        }

        // 将参数传递给MarketBrand对象中去
        MarketBrand marketBrand = new MarketBrand();
        marketBrand.setDesc(description);
        marketBrand.setFloorPrice(floorPrice);
        marketBrand.setName(name);
        marketBrand.setPicUrl(picUrl);
        // 设置添加时间,变量类型为LocalDatetime
        marketBrand.setAddTime(LocalDateTime.now());
        marketBrand.setUpdateTime(LocalDateTime.now());

        // 将封装好的MarketBrand对象传递给Service层
        Object requestBody = ResponseUtil.ok(marketBrandService.create(marketBrand));
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }

    private void listBrand(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer pageNum = Integer.valueOf(req.getParameter("page"));
        Integer pageSize = Integer.valueOf(req.getParameter("limit"));
        String sort = req.getParameter("sort");
        String order = req.getParameter("order");
        List<MarketBrand> brandList = marketBrandService.list(pageNum, pageSize, sort, order);
        Object requestBody = ResponseUtil.okList(brandList);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }
}
