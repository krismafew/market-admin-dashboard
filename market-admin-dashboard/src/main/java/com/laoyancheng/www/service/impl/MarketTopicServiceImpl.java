package com.laoyancheng.www.service.impl;

import com.github.pagehelper.PageHelper;
import com.laoyancheng.www.db.domain.MarketCoupon;
import com.laoyancheng.www.db.domain.MarketTopic;
import com.laoyancheng.www.db.domain.MarketTopicExample;
import com.laoyancheng.www.db.mapper.MarketCouponMapper;
import com.laoyancheng.www.db.mapper.MarketTopicMapper;
import com.laoyancheng.www.service.MarketTopicService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/23 14:41
 */
public class MarketTopicServiceImpl implements MarketTopicService {
    @Override
    public List<MarketTopic> list(Integer pageNum, Integer pageSize, String sort, String order, String title, String subtitle) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketTopicMapper marketTopicMapper = sqlSession.getMapper(MarketTopicMapper.class);
        MarketTopicExample marketTopicExample = new MarketTopicExample();
        MarketTopicExample.Criteria criteria = marketTopicExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(title))
            criteria.andTitleLike("%" + title + "%");
        if(!StringUtils.isEmpty(subtitle))
            criteria.andSubtitleLike("%" + subtitle + "%");
        marketTopicExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(pageNum ,pageSize);
        List<MarketTopic> topicList = marketTopicMapper.selectByExample(marketTopicExample);
        sqlSession.close();
        return topicList;
    }

    @Override
    public MarketTopic create(MarketTopic marketTopic) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketTopicMapper marketTopicMapper = sqlSession.getMapper(MarketTopicMapper.class);
        marketTopicMapper.insertSelective(marketTopic);
        sqlSession.commit();
        sqlSession.close();
        return marketTopic;
    }

    @Override
    public MarketTopic selectOneById(Integer id) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketTopicMapper marketTopicMapper = sqlSession.getMapper(MarketTopicMapper.class);
        MarketTopic marketTopic = marketTopicMapper.selectByPrimaryKey(id);
        sqlSession.close();
        return marketTopic;
    }

    @Override
    public void update(MarketTopic marketTopic) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketTopicMapper marketTopicMapper = sqlSession.getMapper(MarketTopicMapper.class);
        marketTopicMapper.updateByPrimaryKeySelective(marketTopic);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void delete(Integer id) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketTopicMapper marketTopicMapper = sqlSession.getMapper(MarketTopicMapper.class);
        marketTopicMapper.logicalDeleteByPrimaryKey(id);
        sqlSession.commit();
        sqlSession.close();
    }
}
