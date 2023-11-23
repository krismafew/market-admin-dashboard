package com.laoyancheng.www.service.impl;

import com.github.pagehelper.PageHelper;
import com.laoyancheng.www.db.domain.MarketCoupon;
import com.laoyancheng.www.db.domain.MarketCouponExample;
import com.laoyancheng.www.db.mapper.MarketCouponMapper;
import com.laoyancheng.www.service.MarketCouponService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/23 12:14
 */
public class MarketCouponServiceImpl implements MarketCouponService {
    @Override
    public List<MarketCoupon> list(Integer pageNum, Integer pageSize, String sort, String order, String name, Short type, Short status) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketCouponMapper marketCouponMapper = sqlSession.getMapper(MarketCouponMapper.class);
        MarketCouponExample marketCouponExample = new MarketCouponExample();
        MarketCouponExample.Criteria criteria = marketCouponExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(name))
            criteria.andNameLike("%" + name + "%");
        if(type != null)
            criteria.andTypeEqualTo(type);
        if(status != null)
            criteria.andStatusEqualTo(status);
        marketCouponExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(pageNum, pageSize);
        List<MarketCoupon> couponList = marketCouponMapper.selectByExample(marketCouponExample);
        sqlSession.close();
        return couponList;
    }

    @Override
    public Object create(MarketCoupon marketCoupon) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketCouponMapper marketCouponMapper = sqlSession.getMapper(MarketCouponMapper.class);
        marketCouponMapper.insertSelective(marketCoupon);
        sqlSession.commit();
        sqlSession.close();
        return marketCoupon;
    }

    @Override
    public void update(MarketCoupon marketCoupon) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketCouponMapper marketCouponMapper = sqlSession.getMapper(MarketCouponMapper.class);
        marketCouponMapper.updateByPrimaryKeySelective(marketCoupon);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void delete(Integer id) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketCouponMapper marketCouponMapper = sqlSession.getMapper(MarketCouponMapper.class);
        marketCouponMapper.logicalDeleteByPrimaryKey(id);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public MarketCoupon selectCouponById(Integer id) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketCouponMapper marketCouponMapper = sqlSession.getMapper(MarketCouponMapper.class);
        MarketCoupon marketCoupon = marketCouponMapper.selectByPrimaryKey(id);
        sqlSession.close();
        return marketCoupon;
    }
}
