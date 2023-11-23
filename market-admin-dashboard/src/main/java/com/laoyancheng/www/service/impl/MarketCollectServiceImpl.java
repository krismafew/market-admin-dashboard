package com.laoyancheng.www.service.impl;

import com.github.pagehelper.PageHelper;
import com.laoyancheng.www.db.domain.MarketCollect;
import com.laoyancheng.www.db.domain.MarketCollectExample;
import com.laoyancheng.www.db.mapper.MarketCollectMapper;
import com.laoyancheng.www.service.MarketCollectService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/19 15:06
 */
public class MarketCollectServiceImpl implements MarketCollectService {
    @Override
    public List<MarketCollect> list(Integer pageNum, Integer pageSize, String sort, String order, Integer userId, Integer valueId) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketCollectMapper marketCollectMapper = sqlSession.getMapper(MarketCollectMapper.class);
        MarketCollectExample marketCollectExample = new MarketCollectExample();
        MarketCollectExample.Criteria criteria = marketCollectExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if(userId != null)
            criteria.andUserIdEqualTo(userId);
        if(valueId != null)
            criteria.andValueIdEqualTo(valueId);
        marketCollectExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(pageNum, pageSize);
        List<MarketCollect> collectList = marketCollectMapper.selectByExample(marketCollectExample);
        sqlSession.close();
        return collectList;
    }
}
