package com.laoyancheng.www.service;

import com.laoyancheng.www.db.domain.MarketAd;

import java.util.List;

public interface MarketAdService {
    List<MarketAd> list(Integer pageNum, Integer pageSize, String sort, String order);

    Object create(MarketAd marketAd);

    void update(MarketAd marketAd);

    void delete(Integer id);
}
