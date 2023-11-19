package com.laoyancheng.www.service;

import com.laoyancheng.www.db.domain.MarketUser;

import java.util.List;

public interface MarketUserService {
    Integer countUsers();

    List<MarketUser> list(Integer pageNum, Integer pageSize, String sort, String order);
}
