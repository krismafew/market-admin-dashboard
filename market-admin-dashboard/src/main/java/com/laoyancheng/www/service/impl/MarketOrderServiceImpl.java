package com.laoyancheng.www.service.impl;

import com.laoyancheng.www.db.domain.MarketOrderExample;
import com.laoyancheng.www.db.mapper.MarketOrderMapper;
import com.laoyancheng.www.service.MarketOrderService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 * @Description: 商品订单的Service层
 * @Author: JuRan
 * @Date: 2023/11/18 16:15
 */
public class MarketOrderServiceImpl implements MarketOrderService {
    @Override
    public Integer countOrders() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketOrderMapper marketOrderMapper = sqlSession.getMapper(MarketOrderMapper.class);
        MarketOrderExample marketOrderExample = new MarketOrderExample();
        marketOrderExample.or().andDeletedEqualTo(false);
        Integer orderTotal = (int)marketOrderMapper.countByExample(marketOrderExample);
        sqlSession.close();
        return orderTotal;
    }
}
