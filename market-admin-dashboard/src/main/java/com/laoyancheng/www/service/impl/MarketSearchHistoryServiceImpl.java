package com.laoyancheng.www.service.impl;

import com.github.pagehelper.PageHelper;
import com.laoyancheng.www.db.domain.MarketSearchHistory;
import com.laoyancheng.www.db.domain.MarketSearchHistoryExample;
import com.laoyancheng.www.db.mapper.MarketSearchHistoryMapper;
import com.laoyancheng.www.service.MarketSearchHistoryService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/19 16:04
 */
public class MarketSearchHistoryServiceImpl implements MarketSearchHistoryService {
    @Override
    public List<MarketSearchHistory> list(Integer pageNum, Integer pageSize, String sort, String order) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketSearchHistoryMapper marketSearchHistoryMapper = sqlSession.getMapper(MarketSearchHistoryMapper.class);
        MarketSearchHistoryExample marketSearchHistoryExample = new MarketSearchHistoryExample();
        marketSearchHistoryExample.orderBy(sort + " " + order).createCriteria().andDeletedEqualTo(false);
        PageHelper.startPage(pageNum, pageSize);
        List<MarketSearchHistory> historyList = marketSearchHistoryMapper.selectByExample(marketSearchHistoryExample);
        sqlSession.close();
        return historyList;
    }
}
