package com.laoyancheng.www.service;

import com.laoyancheng.www.db.domain.MarketAdmin;

public interface AdminAuthService {

    MarketAdmin login(String username, String password, String localAddr);
}
