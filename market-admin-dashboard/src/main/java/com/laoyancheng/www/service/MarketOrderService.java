package com.laoyancheng.www.service;

import com.laoyancheng.www.db.domain.MarketOrder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface MarketOrderService {
    Integer countOrders();
    List<MarketOrder> list(Integer pageNum, Integer pageSize, String sort, String order, Integer userId, String orderSn, LocalDateTime start, LocalDateTime end, ArrayList<Short> statusList);

    MarketOrder selectOrderById(Integer id);

    void update(MarketOrder marketOrder);

    void delete(Integer orderId);
}
