package com.laoyancheng.www.service;

import com.laoyancheng.www.db.domain.MarketGoodsAttribute;

import java.util.List;

public interface MarketGoodsAttributeService {
    void create(List<MarketGoodsAttribute> attributeList);

    List<MarketGoodsAttribute> selectAttributeListByGoodsId(Integer goodsId);

    void updateAttributeList(List<MarketGoodsAttribute> attributeList);
}
