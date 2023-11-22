package com.laoyancheng.www.controller;

import com.laoyancheng.www.db.domain.MarketComment;
import com.laoyancheng.www.service.MarketCommentService;
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
import java.util.List;

/**
 * @Description: 评论接口
 * @Author: JuRan
 * @Date: 2023/11/22 11:28
 */
@WebServlet("/admin/comment/*")
public class AdminCommentController extends HttpServlet {
    private MarketCommentService marketCommentService = new MarketCommentServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/comment/", "");
        if(StringUtils.equals("list", option)){
            listComment(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/comment/", "");
        if(StringUtils.equals("delete", option)){
            celeteComment(req, resp);
        }
    }

    private void listComment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer pageNum = Integer.valueOf(req.getParameter("page"));
        Integer pageSize = Integer.valueOf(req.getParameter("limit"));
        String sort = req.getParameter("sort");
        String order = req.getParameter("order");

        List<MarketComment> commentList = marketCommentService.list(pageNum, pageSize, sort, order);
        Object requestBody = ResponseUtil.okList(commentList);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));

    }

    private void celeteComment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
        marketCommentService.delete(id);
        resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.ok()));
    }
}
