package com.laoyancheng.www.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laoyancheng.www.db.domain.MarketAdmin;
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
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

@WebServlet("/admin/auth/*")
public class AdminAuthController extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();
    private MarketAdminService marketAdminService = new MarketAdminServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace(req.getContextPath() + "/admin/auth/", "");

        if(StringUtils.equals(option, "info")){
            info(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //如何写下面代码的注释
        String option = req.getRequestURI().replace(req.getContextPath() + "/admin/auth/", "");

        if(StringUtils.equals(option, "login")){
            login(req, resp);
        }else if(StringUtils.equals("logout", option)){
            logout(req, resp);
        }
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        session.invalidate();
        resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.ok()));
    }

    // 处理登录
    // 处理用户的输入:账号和密码
    // 1、账号或者密码为空
    // 2、账号和密码传递给AdminAuthService.login()处理
    //   2.1用户名和密码正确   2.2用户名或密码错误

    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // 将请求体中的数据转化为字符串类型
        ServletInputStream inputStream = req.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int length = 0;
        byte[] bytes = new byte[1024];

        while((length = inputStream.read(bytes)) != -1){
            outputStream.write(bytes, 0, length);
        }


        // Json字符串"{"username" : "admin123", "password" : "admin123"}"
        String jsonString = outputStream.toString("utf-8");

        outputStream.close();
        inputStream.close();
        // 使用Jackson库解析JSON字符串
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        String username = jsonNode.get("username").asText();
        String password = jsonNode.get("password").asText();

        // 如果账号和密码有一个为空
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.badArgumentValue()));
            return;
        }

        // 交给adminAuthService处理登录
        MarketAdmin admin = marketAdminService.login(username, password, req.getLocalAddr());
        // 如果admin为null, 说明账号或密码错误
        if(admin == null)
            resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.wrongUsernameOrPassword()));
        else {
            // 账号和密码正确,登陆成功
            // 返回响应
            HashMap<String, Object> data = new HashMap<>();
            HashMap<String, Object> adminInfo = new HashMap<>();

            adminInfo.put("nickName", admin.getUsername());
            adminInfo.put("avatar", admin.getAvatar());
            data.put("adminInfo", adminInfo);
            // token 是sessionId
            // 登陆成功，创建一个Session对象
            HttpSession session = req.getSession();
            data.put("token", session.getId());

            // 在Session中存入管理员对象，进行会话管理
            session.setAttribute("info", admin);

            resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.ok(data)));
        }
    }

    private void info(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        MarketAdmin admin = (MarketAdmin) session.getAttribute("info");

        // 封装请求体
        HashMap<String, Object> data = new HashMap<>();
        // 封装roles
        data.put("roles", Arrays.asList(new String[]{"超级管理员"}));

        data.put("name", admin.getUsername());
        // 封装perms
        data.put("perms", Arrays.asList(new String[]{"*"}));

        data.put("avatar", admin.getAvatar());
        Object responseBody = ResponseUtil.ok(data);
        resp.getWriter().println(JacksonUtil.writeValueAsString(responseBody));
    }
}
