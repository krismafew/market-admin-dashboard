package com.laoyancheng.www.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laoyancheng.www.db.domain.MarketAdmin;
import com.laoyancheng.www.db.domain.MarketRole;
import com.laoyancheng.www.service.MarketAdminService;
import com.laoyancheng.www.service.impl.MarketAdminServiceImpl;
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
 * @Description: 管理员管理接口
 * @Author: JuRan
 * @Date: 2023/11/23 16:29
 */
@WebServlet("/admin/admin/*")
public class AdminAdminController extends HttpServlet {
    private MarketAdminService marketAdminService = new MarketAdminServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/admin/", "");
        if(StringUtils.equals("list", option)){
            listAdmin(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/admin/", "");
        if(StringUtils.equals("create", option)){
            createAdmin(req, resp);
        }else if(StringUtils.equals("update", option)){
            updateAdmin(req, resp);
        }else if(StringUtils.equals("delete", option)){
            deleteAdmin(req, resp);
        }
    }

    private void deleteAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
        marketAdminService.delete(id);
        resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.ok()));
    }

    private void updateAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

        MarketAdmin marketAdmin = JacksonUtil.getObjectMapper().readValue(jsonStr, MarketAdmin.class);
        marketAdmin.setUpdateTime(LocalDateTime.now());
        marketAdminService.update(marketAdmin);
        Object requestBody = ResponseUtil.ok(marketAdmin);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }

    private void createAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

        MarketAdmin marketAdmin = JacksonUtil.getObjectMapper().readValue(jsonStr, MarketAdmin.class);
        marketAdmin.setAddTime(LocalDateTime.now());
        marketAdmin.setUpdateTime(LocalDateTime.now());

        Object requestBody = ResponseUtil.ok(marketAdminService.create(marketAdmin));
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }

    private void listAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer pageNum = Integer.valueOf(req.getParameter("page"));
        Integer pageSize = Integer.valueOf(req.getParameter("limit"));
        String sort = req.getParameter("sort");
        String order = req.getParameter("order");
        String username = req.getParameter("username");

        List<MarketAdmin> adminList = marketAdminService.list(pageNum, pageSize, sort, order, username);
/*        ArrayList<Object> list = new ArrayList<>();
        for(MarketAdmin admin : adminList){
            HashMap<String, Object> data = new HashMap<>();
            data.put("avatar", admin.getAvatar());
            data.put("id", admin.getId());
            data.put("roleIds", admin.getRoleIds());
            data.put("username", admin.getUsername());
            list.add(data);
        }*/
        Object requestBody = ResponseUtil.okList(adminList);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }
}
