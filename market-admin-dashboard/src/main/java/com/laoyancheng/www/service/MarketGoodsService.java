package com.laoyancheng.www.service;

import com.laoyancheng.www.db.domain.MarketGoods;

import java.util.List;

public interface MarketGoodsService {
    Integer countGoods();

    List<MarketGoods> list(Integer pageNum, Integer pageSize, String sort, String order, String goodsSn, String name, Integer goodsId);

    Integer create(MarketGoods marketGoods);

    void delete(Integer id);

    MarketGoods selectGoodsById(Integer goodsId);

    void updateGoods(MarketGoods marketGoods);
}
