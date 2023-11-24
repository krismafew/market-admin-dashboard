package com.laoyancheng.www.service.impl;

import com.laoyancheng.www.db.domain.MarketUserGrowth;
import com.laoyancheng.www.db.mapper.MarketUserGrowthMapper;
import com.laoyancheng.www.service.MarketUserGrowthService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/24 21:29
 */
public class MarketUserGrowthServiceImpl implements MarketUserGrowthService {
    @Override
    public void recordUserGrowth(Date date, Integer growthNum) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketUserGrowthMapper userGrowthMapper = sqlSession.getMapper(MarketUserGrowthMapper.class);
        userGrowthMapper.insertUserGrowth(growthNum, date);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public List<MarketUserGrowth> list() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketUserGrowthMapper marketUserGrowthMapper = sqlSession.getMapper(MarketUserGrowthMapper.class);
        List<MarketUserGrowth> userGrowthList = marketUserGrowthMapper.selectUserGrowthList();
        sqlSession.close();
        return userGrowthList;
    }
}
