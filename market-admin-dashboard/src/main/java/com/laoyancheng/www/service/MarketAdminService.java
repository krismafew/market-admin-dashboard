package com.laoyancheng.www.service;

import com.laoyancheng.www.db.domain.MarketAdmin;
import com.laoyancheng.www.db.domain.MarketRole;

import java.util.List;

public interface MarketAdminService {

    MarketAdmin login(String username, String password, String localAddr);

    List<MarketAdmin> list(Integer pageNum, Integer pageSize, String sort, String order, String username);

    MarketAdmin create(MarketAdmin marketAdmin);

    void update(MarketAdmin marketAdmin);

    void delete(Integer id);
}
