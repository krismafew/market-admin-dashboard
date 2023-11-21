package com.laoyancheng.www.service.impl;

import com.github.pagehelper.PageHelper;
import com.laoyancheng.www.db.domain.MarketKeyword;
import com.laoyancheng.www.db.domain.MarketKeywordExample;
import com.laoyancheng.www.db.mapper.MarketKeywordMapper;
import com.laoyancheng.www.service.MarketKeywordService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/21 20:43
 */
public class MarketKeywordServiceImpl implements MarketKeywordService {
    @Override
    public List<MarketKeyword> list(Integer pageNum, Integer pageSize, String sort, String order) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketKeywordMapper marketKeywordMapper = sqlSession.getMapper(MarketKeywordMapper.class);
        MarketKeywordExample marketKeywordExample = new MarketKeywordExample();
        marketKeywordExample.orderBy(sort + " " + order).createCriteria().andDeletedEqualTo(false);
        PageHelper.startPage(pageNum, pageSize);
        List<MarketKeyword> keywordList = marketKeywordMapper.selectByExample(marketKeywordExample);
        sqlSession.close();
        return keywordList;
    }

    @Override
    public MarketKeyword create(MarketKeyword marketKeyword) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketKeywordMapper marketKeywordMapper = sqlSession.getMapper(MarketKeywordMapper.class);
        marketKeywordMapper.insertSelective(marketKeyword);
        sqlSession.commit();
        sqlSession.close();
        return marketKeyword;
    }

    @Override
    public void update(MarketKeyword marketKeyword) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketKeywordMapper marketKeywordMapper = sqlSession.getMapper(MarketKeywordMapper.class);
        marketKeywordMapper.updateByPrimaryKeySelective(marketKeyword);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void delete(Integer id) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketKeywordMapper marketKeywordMapper = sqlSession.getMapper(MarketKeywordMapper.class);
        marketKeywordMapper.logicalDeleteByPrimaryKey(id);
        sqlSession.commit();
        sqlSession.close();
    }
}
