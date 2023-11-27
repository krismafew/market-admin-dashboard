package com.laoyancheng.www.service.impl;

import com.laoyancheng.www.db.domain.MarketOrderStat;
import com.laoyancheng.www.db.mapper.MarketOrderStatMapper;
import com.laoyancheng.www.service.MarketOrderStatService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/27 0:01
 */
public class MarketOrderStatServiceImpl implements MarketOrderStatService {
    @Override
    public void create(Date date, BigDecimal amount, int usersPaid, int orders, BigDecimal pcr, Integer products) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketOrderStatMapper marketOrderStatMapper = sqlSession.getMapper(MarketOrderStatMapper.class);
        marketOrderStatMapper.insert(date, amount, usersPaid, orders, pcr, products);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void updateByDate(Date date, BigDecimal amount, Integer users, Integer orders, BigDecimal pcr, Integer products) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketOrderStatMapper marketOrderStatMapper = sqlSession.getMapper(MarketOrderStatMapper.class);
        marketOrderStatMapper.updateByDate(date, amount, users, orders, pcr, products);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public List<MarketOrderStat> list() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketOrderStatMapper marketOrderStatMapper = sqlSession.getMapper(MarketOrderStatMapper.class);
        List<MarketOrderStat> orderStatList = marketOrderStatMapper.selectAll();
        sqlSession.close();
        return orderStatList;
    }
}
