package com.laoyancheng.www.controller;

import com.laoyancheng.www.db.domain.MarketCoupon;
import com.laoyancheng.www.db.domain.MarketCouponUser;
import com.laoyancheng.www.service.MarketCouponService;
import com.laoyancheng.www.service.MarketCouponUserService;
import com.laoyancheng.www.service.impl.MarketCouponServiceImpl;
import com.laoyancheng.www.service.impl.MarketCouponUserServiceImpl;
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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @Description: 优惠券接口
 * @Author: JuRan
 * @Date: 2023/11/23 12:14
 */
@WebServlet("/admin/coupon/*")
public class AdminCouponController extends HttpServlet {
    private MarketCouponService marketCouponService = new MarketCouponServiceImpl();
    private MarketCouponUserService marketCouponUserService = new MarketCouponUserServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/coupon/", "");
        if(StringUtils.equals("list", option)){
            listCoupon(req, resp);
        }else if(StringUtils.equals("listuser", option)){
            listUserByCouponId(req, resp);
        }else if(StringUtils.equals("read", option)){
            listCouponById(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/coupon/", "");
        if(StringUtils.equals("create", option)){
            createCoupon(req, resp);
        }else if(StringUtils.equals("update", option)){
            updateCoupon(req, resp);
        }else if(StringUtils.equals("delete", option)){
            deleteCoupon(req, resp);
        }
    }

    private void listCouponById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));
        MarketCoupon coupon = marketCouponService.selectCouponById(id);
        Object requestBody = ResponseUtil.ok(coupon);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }

    private void listUserByCouponId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer pageNum = Integer.valueOf(req.getParameter("page"));
        Integer pageSize = Integer.valueOf(req.getParameter("limit"));
        Integer couponId = Integer.valueOf(req.getParameter("couponId"));
        String sort = req.getParameter("sort");
        String order = req.getParameter("order");

        List<MarketCouponUser> couponUserList = marketCouponUserService.listUserByCouponId(pageNum, pageSize, sort, order, couponId);
        Object requestBody = ResponseUtil.okList(couponUserList);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));

    }

    private void deleteCoupon(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

        marketCouponService.delete(id);
        resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.ok()));
    }

    private void updateCoupon(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

        // 解析Json字符串，封装到新建的MarketCoupon对象中去
        // 将字符串转化为LocalDateTime类型
        String addTimeStr = JacksonUtil.parseString(jsonStr, "addTime");
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime addTime = parseJsonDateStr(addTimeStr, pattern);
        Short days = Short.valueOf(JacksonUtil.parseString(jsonStr, "days"));
        String desc = JacksonUtil.parseString(jsonStr, "desc");
        BigDecimal discount = new BigDecimal(JacksonUtil.parseString(jsonStr, "discount"));
        String endTimeStr = JacksonUtil.parseString(jsonStr, "endTime");
        LocalDateTime endTime = null;
        if(!StringUtils.equals("null", endTimeStr))
            endTime = parseJsonDateStr(endTimeStr, pattern);
        Short goodsType = Short.valueOf(JacksonUtil.parseString(jsonStr, "goodsType"));
        Integer id = Integer.valueOf(JacksonUtil.parseString(jsonStr, "id"));
        Short limit = Short.valueOf(JacksonUtil.parseString(jsonStr, "limit"));
        BigDecimal min = new BigDecimal(JacksonUtil.parseString(jsonStr, "min"));
        String name = JacksonUtil.parseString(jsonStr, "name");
        String startTimeStr = JacksonUtil.parseString(jsonStr, "startTime");
        LocalDateTime startTime = null;
        if(!StringUtils.equals("null", endTimeStr))
            startTime = parseJsonDateStr(startTimeStr, pattern);
        Short status = Short.valueOf(JacksonUtil.parseString(jsonStr, "status"));
        String tag = JacksonUtil.parseString(jsonStr, "tag");
        Short timeType = Short.valueOf(JacksonUtil.parseString(jsonStr, "timeType"));
        Integer total = Integer.valueOf(JacksonUtil.parseString(jsonStr, "total"));
        Short type = Short.valueOf(JacksonUtil.parseString(jsonStr, "type"));

        MarketCoupon marketCoupon = new MarketCoupon();
        marketCoupon.setUpdateTime(LocalDateTime.now());
        marketCoupon.setAddTime(addTime);
        marketCoupon.setDays(days);
        marketCoupon.setDesc(desc);
        marketCoupon.setDiscount(discount);
        marketCoupon.setEndTime(endTime);
        marketCoupon.setGoodsType(goodsType);
        marketCoupon.setId(id);
        marketCoupon.setLimit(limit);
        marketCoupon.setMin(min);
        marketCoupon.setName(name);
        marketCoupon.setStartTime(startTime);
        marketCoupon.setStatus(status);
        marketCoupon.setTag(tag);
        marketCoupon.setTimeType(timeType);
        marketCoupon.setTotal(total);
        marketCoupon.setType(type);

        marketCouponService.update(marketCoupon);
        Object requestBody = ResponseUtil.ok(marketCoupon);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }

    private LocalDateTime parseJsonDateStr(String addTime, DateTimeFormatter pattern) {
        return LocalDateTime.parse(addTime, pattern);
    }

    private void createCoupon(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

        MarketCoupon marketCoupon = JacksonUtil.getObjectMapper().readValue(jsonStr, MarketCoupon.class);
        marketCoupon.setAddTime(LocalDateTime.now());
        marketCoupon.setUpdateTime(LocalDateTime.now());

        Object requestBody = ResponseUtil.ok(marketCouponService.create(marketCoupon));
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }

    private void listCoupon(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer pageNum = Integer.valueOf(req.getParameter("page"));
        Integer pageSize = Integer.valueOf(req.getParameter("limit"));
        String sort = req.getParameter("sort");
        String order = req.getParameter("order");
        String name = req.getParameter("name");
        String statusStr = req.getParameter("status");
        String typeStr = req.getParameter("type");
        Short status = null;
        Short type = null;
        if(!StringUtils.isEmpty(statusStr))
            status = Short.valueOf(statusStr);
        if(!StringUtils.isEmpty(typeStr))
            type = Short.valueOf(typeStr);

        List<MarketCoupon> couponList = marketCouponService.list(pageNum, pageSize, sort, order, name, type ,status);
        Object requestBody = ResponseUtil.okList(couponList);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }
}
