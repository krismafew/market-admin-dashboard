package com.laoyancheng.www.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/30 17:16
 */
@WebListener
public class AuthenticateListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        InputStream inputStream = AuthenticateListener.class.getClassLoader().getResourceAsStream("auth.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        ArrayList<String> uriList = new ArrayList<>();
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null){
                uriList.add(line);
            }
            servletContextEvent.getServletContext().setAttribute("uriList", uriList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
