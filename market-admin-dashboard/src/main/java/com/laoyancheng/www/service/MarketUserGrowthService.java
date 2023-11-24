package com.laoyancheng.www.service;

import com.laoyancheng.www.db.domain.MarketUserGrowth;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

public interface MarketUserGrowthService {

    void recordUserGrowth(Date date, Integer growthNum);

    List<MarketUserGrowth> list();
}
