package com.laoyancheng.www.service.impl;

import com.github.pagehelper.PageHelper;
import com.laoyancheng.www.db.domain.MarketCouponUser;
import com.laoyancheng.www.db.domain.MarketCouponUserExample;
import com.laoyancheng.www.db.mapper.MarketCouponUserMapper;
import com.laoyancheng.www.service.MarketCouponUserService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/23 14:26
 */
public class MarketCouponUserServiceImpl implements MarketCouponUserService {
    @Override
    public List<MarketCouponUser> listUserByCouponId(Integer pageNum, Integer pageSize, String sort, String order, Integer couponId) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketCouponUserMapper marketCouponUserMapper = sqlSession.getMapper(MarketCouponUserMapper.class);
        MarketCouponUserExample marketCouponUserExample = new MarketCouponUserExample();
        MarketCouponUserExample.Criteria criteria = marketCouponUserExample.createCriteria();
        criteria.andDeletedEqualTo(false)
                .andCouponIdEqualTo(couponId);
        marketCouponUserExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(pageNum, pageSize);
        List<MarketCouponUser> couponUserList = marketCouponUserMapper.selectByExample(marketCouponUserExample);
        sqlSession.close();
        return couponUserList;
    }
}
