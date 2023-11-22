package com.laoyancheng.www.service;

import com.laoyancheng.www.db.domain.MarketGoodsSpecification;

import java.util.List;

public interface MarketGoodsSpecificationService {
    void create(List<MarketGoodsSpecification> specificationList);

    List<MarketGoodsSpecification> selectSpecificationListByGoodsId(Integer goodsId);

    void updateSpecificationList(List<MarketGoodsSpecification> specificationList);
}
