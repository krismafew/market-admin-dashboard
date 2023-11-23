package com.laoyancheng.www.service;

import com.laoyancheng.www.db.domain.MarketIssue;

import java.util.List;

public interface MarketIssueService {
    List<MarketIssue> list(Integer pageNum, Integer pageSize, String sort, String order, String question);

    Object create(MarketIssue marketIssue);

    void update(MarketIssue marketIssue);

    void delete(Integer id);
}
