package com.laoyancheng.www.service;

import com.laoyancheng.www.db.domain.MarketComment;

import java.util.List;

public interface MarketCommentService {
    List<MarketComment> list(Integer pageNum, Integer pageSize, String sort, String order, Integer userId, Integer valueId);

    void delete(Integer id);
}
