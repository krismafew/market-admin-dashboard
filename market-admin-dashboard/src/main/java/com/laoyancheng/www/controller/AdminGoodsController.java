package com.laoyancheng.www.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.laoyancheng.www.db.DTO.MarketBrandLabelDTO;
import com.laoyancheng.www.db.DTO.MarketCategoryLabelDTO;
import com.laoyancheng.www.db.domain.MarketGoods;
import com.laoyancheng.www.db.domain.MarketGoodsAttribute;
import com.laoyancheng.www.db.domain.MarketGoodsProduct;
import com.laoyancheng.www.db.domain.MarketGoodsSpecification;
import com.laoyancheng.www.db.mapper.MarketGoodsMapper;
import com.laoyancheng.www.service.*;
import com.laoyancheng.www.service.impl.*;
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
 * @Description: 商品管理接口
 * @Author: JuRan
 * @Date: 2023/11/21 21:10
 */
@WebServlet("/admin/goods/*")
public class AdminGoodsController extends HttpServlet {
    private MarketGoodsService marketGoodsService = new MarketGoodsServiceImpl();
    private MarketCategoryService marketCategoryService = new MarketCategoryServiceImpl();
    private MarketBrandService marketBrandService = new MarketBrandServiceImpl();
    private MarketGoodsAttributeService marketGoodsAttributeService = new MarketGoodsAttributeServiceImpl();
    private MarketGoodsSpecificationService marketGoodsSpecificationService = new MarketGoodsSpecificationServiceImpl();
    private MarketGoodsProductService marketGoodsProductService = new MarketGoodsProductServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/goods/", "");
        if(StringUtils.equals("list", option)){
            listGoods(req, resp);
        }else if(StringUtils.equals("catAndBrand", option)){
            listCategoryAndBrand(req, resp);
        }else if(StringUtils.equals("detail", option)){
            listGoodsDetail(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/goods/", "");
        if(StringUtils.equals("create", option)){
            createGoods(req, resp);
        }else if(StringUtils.equals("delete", option)){
            deleteGoods(req, resp);
        }else if(StringUtils.equals("update", option)){
            updateGoods(req, resp);
        }
    }

    private void updateGoods(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

        ObjectMapper objectMapper = JacksonUtil.getObjectMapper();
        MarketGoods marketGoods = objectMapper.readValue(objectMapper.readTree(jsonStr).get("goods").toString(), MarketGoods.class);
        marketGoodsService.updateGoods(marketGoods);

        TypeFactory typeFactory = objectMapper.getTypeFactory();
        CollectionType attributeCollectionType = typeFactory.constructCollectionType(List.class, MarketGoodsAttribute.class);
        List<MarketGoodsAttribute> attributeList = objectMapper.readValue(objectMapper.readTree(jsonStr).get("attributes").toString(), attributeCollectionType);
        marketGoodsAttributeService.updateAttributeList(attributeList);

        typeFactory.clearCache();
        CollectionType productCollectionType = typeFactory.constructCollectionType(List.class, MarketGoodsProduct.class);
        List<MarketGoodsProduct> productList = objectMapper.readValue(objectMapper.readTree(jsonStr).get("products").toString(), productCollectionType);
        marketGoodsProductService.updateProductList(productList);

        typeFactory.clearCache();
        CollectionType specificationCollectionType = typeFactory.constructCollectionType(List.class, MarketGoodsSpecification.class);
        List<MarketGoodsSpecification> specificationList = objectMapper.readValue(objectMapper.readTree(jsonStr).get("specifications").toString(), specificationCollectionType);
        marketGoodsSpecificationService.updateSpecificationList(specificationList);

        resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.ok()));
    }

    private void listGoodsDetail(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer goodsId = Integer.valueOf(req.getParameter("id"));
        HashMap<String, Object> data = new HashMap<>();
        MarketGoods goods = marketGoodsService.selectGoodsById(goodsId);
        List<MarketGoodsAttribute> attributeList = marketGoodsAttributeService.selectAttributeListByGoodsId(goodsId);
        List<Integer> categoryIdList = marketCategoryService.selectCategoryIdHierarchyListByL2(goods.getCategoryId());
        List<MarketGoodsProduct> productList = marketGoodsProductService.selectProductListByGoodsId(goodsId);
        List<MarketGoodsSpecification> specificationList = marketGoodsSpecificationService.selectSpecificationListByGoodsId(goodsId);

        data.put("attributes", attributeList);
        data.put("categoryIds", categoryIdList);
        data.put("goods", goods);
        data.put("products", productList);
        data.put("specifications", specificationList);

        Object requestBody = ResponseUtil.ok(data);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));

    }

    private void listCategoryAndBrand(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HashMap<String, Object> data = new HashMap<>();

        List<MarketCategoryLabelDTO> categoryLabelDTOList = marketCategoryService.listLabel();
        List<MarketBrandLabelDTO> brandLabelDTOList = marketBrandService.listLabel();

        data.put("brandList", brandLabelDTOList);
        data.put("categoryList", categoryLabelDTOList);
        Object requestBody = ResponseUtil.ok(data);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }

    private void deleteGoods(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

        marketGoodsService.delete(id);
        resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.ok()));
    }

    private void createGoods(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

        ObjectMapper objectMapper = JacksonUtil.getObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        CollectionType attributeCollectionType = typeFactory.constructCollectionType(List.class, MarketGoodsAttribute.class);
        List<MarketGoodsAttribute> attributeList = objectMapper.readValue(objectMapper.readTree(jsonStr).get("attributes").toString(), attributeCollectionType);

        typeFactory.clearCache();
        CollectionType specificationCollectionType = typeFactory.constructCollectionType(List.class, MarketGoodsSpecification.class);
        List<MarketGoodsSpecification> specificationList = objectMapper.readValue(objectMapper.readTree(jsonStr).get("specifications").toString(), specificationCollectionType);

        typeFactory.clearCache();
        CollectionType productCollectionType = typeFactory.constructCollectionType(List.class, MarketGoodsProduct.class);
        List<MarketGoodsProduct> productList = objectMapper.readValue(objectMapper.readTree(jsonStr).get("products").toString(), productCollectionType);

        MarketGoods marketGoods = objectMapper.readValue(objectMapper.readTree(jsonStr).get("goods").toString(), MarketGoods.class);
        marketGoods.setAddTime(LocalDateTime.now());
        marketGoods.setUpdateTime(LocalDateTime.now());

        Integer goodsId = marketGoodsService.create(marketGoods);


        for(MarketGoodsAttribute attribute: attributeList){
            attribute.setGoodsId(goodsId);
            attribute.setUpdateTime(LocalDateTime.now());
            attribute.setAddTime(LocalDateTime.now());
        }
        // create商品属性
        marketGoodsAttributeService.create(attributeList);

        for(MarketGoodsSpecification specification : specificationList){
            specification.setGoodsId(goodsId);
            specification.setAddTime(LocalDateTime.now());
            specification.setUpdateTime(LocalDateTime.now());
        }

        marketGoodsSpecificationService.create(specificationList);

        for(MarketGoodsProduct product: productList){
            product.setGoodsId(goodsId);
            product.setId(null);
            product.setUpdateTime(LocalDateTime.now());
            product.setAddTime(LocalDateTime.now());
        }

        marketGoodsProductService.create(productList);

        resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.ok()));




    }

    private void listGoods(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer pageNum = Integer.valueOf(req.getParameter("page"));
        Integer pageSize = Integer.valueOf(req.getParameter("limit"));
        String sort = req.getParameter("sort");
        String order = req.getParameter("order");
        String goodsSn = req.getParameter("goodsSn");
        String name = req.getParameter("name");
        String goodsIdStr = req.getParameter("goodsId");
        Integer goodsId = null;
        if(!StringUtils.isEmpty(goodsIdStr))
            goodsId = Integer.valueOf(goodsIdStr);

        List<MarketGoods> goodsList = marketGoodsService.list(pageNum, pageSize, sort, order, goodsSn, name, goodsId);
        Object requestBody = ResponseUtil.okList(goodsList);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }
}
