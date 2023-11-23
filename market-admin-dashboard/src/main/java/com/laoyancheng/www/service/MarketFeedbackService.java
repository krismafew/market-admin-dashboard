package com.laoyancheng.www.service;

import com.laoyancheng.www.db.domain.MarketFeedback;

import java.util.List;

public interface MarketFeedbackService {
    List<MarketFeedback> list(Integer pageNum, Integer pageSize, String sort, String order, String username, Integer id);
}
