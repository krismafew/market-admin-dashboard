package com.laoyancheng.www.service.impl;

import com.github.pagehelper.PageHelper;
import com.laoyancheng.www.db.domain.MarketAd;
import com.laoyancheng.www.db.domain.MarketAdExample;
import com.laoyancheng.www.db.mapper.MarketAdMapper;
import com.laoyancheng.www.service.MarketAdService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/22 11:43
 */
public class MarketAdServiceImpl implements MarketAdService {
    @Override
    public List<MarketAd> list(Integer pageNum, Integer pageSize, String sort, String order, String name, String content) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketAdMapper marketAdMapper = sqlSession.getMapper(MarketAdMapper.class);
        MarketAdExample marketAdExample = new MarketAdExample();
        MarketAdExample.Criteria criteria = marketAdExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(name))
            criteria.andNameLike("%" + name + "%");
        if(!StringUtils.isEmpty(content))
            criteria.andContentLike("%" + content + "%");
        marketAdExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(pageNum, pageSize);
        List<MarketAd> adList = marketAdMapper.selectByExample(marketAdExample);
        sqlSession.close();
        return adList;
    }

    @Override
    public Object create(MarketAd marketAd) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketAdMapper marketAdMapper = sqlSession.getMapper(MarketAdMapper.class);
        marketAdMapper.insertSelective(marketAd);
        sqlSession.commit();
        sqlSession.close();
        return marketAd;
    }

    @Override
    public void update(MarketAd marketAd) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketAdMapper marketAdMapper = sqlSession.getMapper(MarketAdMapper.class);
        marketAdMapper.updateByPrimaryKeySelective(marketAd);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void delete(Integer id) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketAdMapper marketAdMapper = sqlSession.getMapper(MarketAdMapper.class);
        marketAdMapper.logicalDeleteByPrimaryKey(id);
        sqlSession.commit();
        sqlSession.close();
    }
}
