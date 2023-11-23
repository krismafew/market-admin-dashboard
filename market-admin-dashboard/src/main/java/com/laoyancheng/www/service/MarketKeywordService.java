package com.laoyancheng.www.service;

import com.laoyancheng.www.db.domain.MarketKeyword;

import java.util.List;

public interface MarketKeywordService {
    List<MarketKeyword> list(Integer pageNum, Integer pageSize, String sort, String order, String keyword, String url);

    MarketKeyword create(MarketKeyword marketKeyword);

    void update(MarketKeyword marketKeyword);

    void delete(Integer id);
}
