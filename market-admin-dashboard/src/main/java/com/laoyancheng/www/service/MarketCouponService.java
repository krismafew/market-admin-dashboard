package com.laoyancheng.www.service;

import com.laoyancheng.www.db.domain.MarketCoupon;
import com.laoyancheng.www.db.domain.MarketCouponUser;

import java.util.List;

public interface MarketCouponService {
    List<MarketCoupon> list(Integer pageNum, Integer pageSize, String sort, String order, String name, Short type, Short status);

    Object create(MarketCoupon marketCoupon);

    void update(MarketCoupon marketCoupon);

    void delete(Integer id);

    MarketCoupon selectCouponById(Integer id);
}
