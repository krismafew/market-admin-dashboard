package com.laoyancheng.www.service;

import com.laoyancheng.www.db.domain.MarketCollect;

import java.util.List;

public interface MarketCollectService {
    List<MarketCollect> list(Integer pageNum, Integer pageSize, String sort, String order);
}
