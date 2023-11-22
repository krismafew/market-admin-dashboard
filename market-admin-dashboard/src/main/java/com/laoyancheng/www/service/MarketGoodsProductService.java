package com.laoyancheng.www.service;

import com.laoyancheng.www.db.domain.MarketGoodsProduct;

import java.util.List;

public interface MarketGoodsProductService {
    Integer countProducts();

    void create(List<MarketGoodsProduct> productList);

    List<MarketGoodsProduct> selectProductListByGoodsId(Integer goodsId);

    void updateProductList(List<MarketGoodsProduct> productList);
}
