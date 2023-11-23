package com.laoyancheng.www.service;

import com.laoyancheng.www.db.domain.MarketCoupon;
import com.laoyancheng.www.db.domain.MarketTopic;

import java.util.List;

public interface MarketTopicService {
    List<MarketTopic> list(Integer pageNum, Integer pageSize, String sort, String order, String title, String subtitle);

    MarketTopic create(MarketTopic marketTopic);

    MarketTopic selectOneById(Integer id);

    void update(MarketTopic marketTopic);

    void delete(Integer id);
}
