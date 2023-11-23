package com.laoyancheng.www.controller;

import com.laoyancheng.www.db.DTO.MarketRoleDTO;
import com.laoyancheng.www.service.MarketRoleService;
import com.laoyancheng.www.service.impl.MarketRoleServiceImpl;
import com.laoyancheng.www.util.JacksonUtil;
import com.laoyancheng.www.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Description: 管理员身份接口
 * @Author: JuRan
 * @Date: 2023/11/23 16:59
 */
@WebServlet("/admin/role/*")
public class AdminRoleController extends HttpServlet {
    private MarketRoleService marketRoleService = new MarketRoleServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/role/", "");
        if(StringUtils.equals("options", option)){
            listAdminRoleLabel(req, resp);
        }
    }

    private void listAdminRoleLabel(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<MarketRoleDTO> roleLabelList = marketRoleService.listAdminRoleLabel();
        Object requestBody = ResponseUtil.okList(roleLabelList);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }
}
