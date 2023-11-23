package com.laoyancheng.www.service;

import com.laoyancheng.www.db.domain.MarketLog;

import java.util.List;

public interface MarketLogService {
    List<MarketLog> list(Integer pageNum, Integer pageSize, String sort, String order, String name);
}
