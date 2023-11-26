package com.laoyancheng.www.service;

import com.laoyancheng.www.db.domain.MarketOrderGoods;

import java.util.List;

public interface MarketOrderGoodsService {
    List<MarketOrderGoods> selectOrderGoodsListByOrderId(Integer orderId);
}
