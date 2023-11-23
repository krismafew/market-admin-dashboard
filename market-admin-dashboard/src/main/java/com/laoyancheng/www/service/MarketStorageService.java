package com.laoyancheng.www.service;

import com.laoyancheng.www.db.domain.MarketStorage;

import java.util.List;

public interface MarketStorageService {
    MarketStorage create(MarketStorage marketStorage);

    MarketStorage selectStorageByKey(String key);

    List<MarketStorage> list(Integer pageNum, Integer pageSize, String sort, String order, String key, String name);

    void update(MarketStorage marketStorage);

    void delete(Integer id);
}
