package com.laoyancheng.www.service;

import com.laoyancheng.www.db.domain.MarketSearchHistory;

import java.util.List;

public interface MarketSearchHistoryService {
    List<MarketSearchHistory> list(Integer pageNum, Integer pageSize, String sort, String order, Integer userId, String keyword);
}
