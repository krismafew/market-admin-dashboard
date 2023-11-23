package com.laoyancheng.www.service;

import com.laoyancheng.www.db.domain.MarketFootprint;

import java.util.List;

public interface MarketFootprintService {
    List<MarketFootprint> list(Integer pageNum, Integer pageSize, String sort, String order, Integer userId, Integer goodsId);
}
