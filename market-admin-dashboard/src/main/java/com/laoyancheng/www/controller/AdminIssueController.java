package com.laoyancheng.www.controller;

import com.laoyancheng.www.db.domain.MarketIssue;
import com.laoyancheng.www.service.MarketIssueService;
import com.laoyancheng.www.service.impl.MarketIssueServiceImpl;
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
 * @Description: 通用问题接口
 * @Author: JuRan
 * @Date: 2023/11/21 17:31
 */
@WebServlet("/admin/issue/*")
public class AdminIssueController extends HttpServlet {
    private MarketIssueService marketIssueService = new MarketIssueServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/issue/", "");
        if(StringUtils.equals("list", option)){
            listIssue(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/issue/", "");
        if(StringUtils.equals("create", option)){
            createIssue(req, resp);
        }else if(StringUtils.equals("update", option)){
            updateIssue(req, resp);
        }else if(StringUtils.equals("delete", option)){
            deleteIssue(req, resp);
        }
    }

    private void deleteIssue(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

        marketIssueService.delete(id);
        resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.ok()));
    }

    private void updateIssue(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
        String answer = JacksonUtil.parseString(jsonStr, "answer");
        String question = JacksonUtil.parseString(jsonStr, "question");
        Integer id = Integer.valueOf(JacksonUtil.parseString(jsonStr, "id"));

        MarketIssue marketIssue = new MarketIssue();
        marketIssue.setId(id);
        marketIssue.setAnswer(answer);
        marketIssue.setQuestion(question);
        marketIssue.setUpdateTime(LocalDateTime.now());

        Object requestBody = ResponseUtil.ok(marketIssue);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));

    }

    private void createIssue(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
        String answer = JacksonUtil.parseString(jsonStr, "answer");
        String question = JacksonUtil.parseString(jsonStr, "question");

        // 封装marketIssue对象
        MarketIssue marketIssue = new MarketIssue();
        marketIssue.setAnswer(answer);
        marketIssue.setQuestion(question);
        marketIssue.setUpdateTime(LocalDateTime.now());
        marketIssue.setAddTime(LocalDateTime.now());

        Object requestBody = ResponseUtil.ok(marketIssueService.create(marketIssue));
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }

    private void listIssue(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer pageNum = Integer.valueOf(req.getParameter("page"));
        Integer pageSize = Integer.valueOf(req.getParameter("limit"));
        String sort = req.getParameter("sort");
        String order = req.getParameter("order");
        String question = req.getParameter("question");

        List<MarketIssue> issueList = marketIssueService.list(pageNum, pageSize, sort, order, question);
        Object requestBody = ResponseUtil.okList(issueList);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }
}
