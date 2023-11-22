package com.laoyancheng.www.service;

import com.laoyancheng.www.db.domain.MarketStorage;

public interface MarketStorageService {
    MarketStorage create(MarketStorage marketStorage);

    MarketStorage selectStorageByKey(String key);
}
