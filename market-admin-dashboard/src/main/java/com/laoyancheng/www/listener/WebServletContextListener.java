package com.laoyancheng.www.listener;

import com.laoyancheng.www.db.job.UserGrowthStatJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description: 应用启动将配置文件加载到tomcat内存中
 * @Author: JuRan
 * @Date: 2023/11/22 15:52
 */
@WebListener
public class WebServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ClassLoader classLoader = WebServletContextListener.class.getClassLoader();
        InputStream resourceAsStream = classLoader.getResourceAsStream("application.properties");
        Properties properties = new Properties();
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ServletContext context = servletContextEvent.getServletContext();
        context.setAttribute("url", properties.getProperty("url"));
        context.setAttribute("filePath", properties.getProperty("filePath"));

        // 启动定时任务
        JobDetail userGrowthStatJobDetail = JobBuilder.newJob(UserGrowthStatJob.class)
                .withIdentity("userGrowthStatJob", "stat")
                .build();

        CronTrigger userGrowthRrigger = TriggerBuilder.newTrigger()
                .withIdentity("newDay", "stat")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(0, 0))
                .build();

        Scheduler scheduler = null;
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(userGrowthStatJobDetail, userGrowthRrigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
