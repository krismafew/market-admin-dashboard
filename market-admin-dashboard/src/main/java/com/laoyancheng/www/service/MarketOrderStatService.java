package com.laoyancheng.www.service;

import com.laoyancheng.www.db.domain.MarketOrderStat;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public interface MarketOrderStatService {
    void create(Date date, BigDecimal amount, int usersPaid, int orders, BigDecimal pcr, Integer products);

    void updateByDate(Date date, BigDecimal amount, Integer users, Integer orders, BigDecimal pcr, Integer products);

    List<MarketOrderStat> list();
}
