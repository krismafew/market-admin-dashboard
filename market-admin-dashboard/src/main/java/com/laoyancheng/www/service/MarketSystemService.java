package com.laoyancheng.www.service;

import com.laoyancheng.www.db.domain.MarketSystem;

import java.util.HashMap;

public interface MarketSystemService {

    void updateConfig(MarketSystem marketSystem);

    MarketSystem getConfig(String keyName);
}
