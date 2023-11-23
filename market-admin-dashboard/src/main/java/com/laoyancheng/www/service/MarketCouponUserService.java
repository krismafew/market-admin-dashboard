package com.laoyancheng.www.service;

import com.laoyancheng.www.db.domain.MarketCouponUser;

import java.util.List;

public interface MarketCouponUserService {
    List<MarketCouponUser> listUserByCouponId(Integer pageNum, Integer pageSize, String sort, String order, Integer couponId);
}
