package com.laoyancheng.www.service;

import com.laoyancheng.www.db.domain.MarketAddress;

import java.util.List;

public interface MarketAddressService {
    List<MarketAddress> list(Integer pageNum, Integer pageSize, String sort, String order, String name, Integer userId);
}
