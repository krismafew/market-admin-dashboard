package com.laoyancheng.www.controller;

import com.laoyancheng.www.db.DTO.MarketRegionDTO;
import com.laoyancheng.www.service.MarketRegionService;
import com.laoyancheng.www.service.impl.MarketRegionServiceImpl;
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
 * @Description: 地区region接口
 * @Author: JuRan
 * @Date: 2023/11/25 10:44
 */
@WebServlet("/admin/region/*")
public class AdminRegionController extends HttpServlet {
    private MarketRegionService marketRegionService = new MarketRegionServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/region/", "");
        if(StringUtils.equals("list", option)){
            listRegion(req, resp);
        }
    }

    private void listRegion(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<MarketRegionDTO> regionDTOList = marketRegionService.list();
        Object requestBody = ResponseUtil.okList(regionDTOList);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }
}
