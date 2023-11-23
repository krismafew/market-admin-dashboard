package com.laoyancheng.www.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.laoyancheng.www.db.domain.MarketStorage;
import com.laoyancheng.www.service.MarketStorageService;
import com.laoyancheng.www.service.impl.MarketStorageServiceImpl;
import com.laoyancheng.www.util.JacksonUtil;
import com.laoyancheng.www.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 图片上传
 * @Author: JuRan
 * @Date: 2023/11/22 15:13
 */
@MultipartConfig
@WebServlet("/admin/storage/*")
public class AdminStorageController extends HttpServlet {
    private MarketStorageService marketStorageService = new MarketStorageServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/storage/", "");
        if(option.startsWith("fetch")){
            fetchStorage(req, resp);
        }else if(StringUtils.equals("list", option)){
            listStorage(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getRequestURI().replace("/admin/storage/", "");
        if(StringUtils.equals("create", option)){
            createStorage(req, resp);
        }else if(StringUtils.equals("update", option)){
            updateStorage(req, resp);
        }else if(StringUtils.equals("delete", option)){
            deleteStorage(req, resp);
        }
    }

    private void deleteStorage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

        marketStorageService.delete(id);
        resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.ok()));
    }

    private void updateStorage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
        MarketStorage marketStorage = JacksonUtil.getObjectMapper().readValue(jsonStr, MarketStorage.class);
        marketStorage.setUpdateTime(LocalDateTime.now());

        marketStorageService.update(marketStorage);
        Object requestBody = ResponseUtil.ok(marketStorage);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }

    private void listStorage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer pageNum = Integer.valueOf(req.getParameter("page"));
        Integer pageSize = Integer.valueOf(req.getParameter("limit"));
        String sort = req.getParameter("sort");
        String order = req.getParameter("order");
        String key = req.getParameter("key");
        String name = req.getParameter("name");

        List<MarketStorage> storageList = marketStorageService.list(pageNum, pageSize, sort, order, key, name);
        Object requestBody = ResponseUtil.okList(storageList);
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }

    private void fetchStorage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String key = req.getRequestURI().replace("/admin/storage/fetch/", "");
        ServletContext context = getServletContext();
        MarketStorage storage = marketStorageService.selectStorageByKey(key);
        String filePath = (String)context.getAttribute("filePath");
        char[] charArray = Integer.toHexString(key.hashCode()).toCharArray();
        for(char character : charArray){
            filePath += character + "/";
        }
        filePath += key;

        File file = new File(filePath);
        if(!file.exists()){
            resp.getWriter().println(JacksonUtil.writeValueAsString(ResponseUtil.fileNotFound()));
            return;
        }

        FileInputStream inputStream = new FileInputStream(file);
        ServletOutputStream outputStream = resp.getOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;

        while((len = inputStream.read(buffer)) != -1){
            outputStream.write(buffer, 0, len);
        }

        inputStream.close();
        outputStream.close();
    }

    private void createStorage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ServletContext context = getServletContext();
        Part file = req.getPart("file");
        String name = file.getSubmittedFileName();
        String key = UUID.randomUUID() + getFileExtentionRegex(name);
        int size = (int)file.getSize();
        String type = file.getContentType();
        String hexString = Integer.toHexString(key.hashCode());
        char[] charArray = hexString.toCharArray();
        String storagePath = (String)context.getAttribute("filePath");
        for(char character: charArray){
            storagePath += character + "/";
        }

        storagePath += key;
        File file1 = new File(storagePath);
        if(!file1.getParentFile().exists()){
            file1.getParentFile().mkdirs();
        }

        file.write(storagePath);

        String url = context.getAttribute("url") + key;

        MarketStorage marketStorage = new MarketStorage();
        marketStorage.setAddTime(LocalDateTime.now());
        marketStorage.setKey(key);
        marketStorage.setName(name);
        marketStorage.setSize(size);
        marketStorage.setType(type);
        marketStorage.setUpdateTime(LocalDateTime.now());
        marketStorage.setUrl(url);

        Object requestBody = ResponseUtil.ok(marketStorageService.create(marketStorage));
        resp.getWriter().println(JacksonUtil.writeValueAsString(requestBody));
    }

    private static String getFileExtentionRegex(String name) {
        Pattern pattern = Pattern.compile("(\\.[^.]+)$");
        Matcher matcher = pattern.matcher(name);

        if(matcher.find())
            return matcher.group(1);
        else
            return null;
    }
}
