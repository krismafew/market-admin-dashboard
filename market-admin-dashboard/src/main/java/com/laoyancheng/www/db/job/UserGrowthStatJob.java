package com.laoyancheng.www.db.job;

import com.laoyancheng.www.service.MarketUserGrowthService;
import com.laoyancheng.www.service.MarketUserService;
import com.laoyancheng.www.service.impl.MarketUserGrowthServiceImpl;
import com.laoyancheng.www.service.impl.MarketUserServiceImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @Description: 统计用户增长
 * @Author: JuRan
 * @Date: 2023/11/24 19:52
 */
public class UserGrowthStatJob implements Job {
    private MarketUserService marketUserService = new MarketUserServiceImpl();
    private MarketUserGrowthService marketUserGrowthService = new MarketUserGrowthServiceImpl();
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime startTime = currentTime.minusHours(24).with(LocalTime.of(0, 0, 0));
        LocalDateTime endTime = startTime.with(LocalTime.of(23, 59, 59));
        Integer growthNum = marketUserService.trackUserGrowth(startTime.toString(), endTime.toString());
        marketUserGrowthService.recordUserGrowth(Date.valueOf(startTime.toLocalDate()), growthNum);
    }
}
