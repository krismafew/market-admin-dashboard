package com.laoyancheng.www.service;

import com.laoyancheng.www.db.domain.MarketAdmin;

public interface MarketAdminService {

    MarketAdmin login(String username, String password, String localAddr);
}
