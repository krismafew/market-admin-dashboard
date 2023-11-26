package com.laoyancheng.www.service.impl;

import com.laoyancheng.www.db.domain.MarketOrderGoods;
import com.laoyancheng.www.db.domain.MarketOrderGoodsExample;
import com.laoyancheng.www.db.mapper.MarketOrderGoodsMapper;
import com.laoyancheng.www.service.MarketOrderGoodsService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/26 11:42
 */
public class MarketOrderGoodsServiceImpl implements MarketOrderGoodsService {
    @Override
    public List<MarketOrderGoods> selectOrderGoodsListByOrderId(Integer orderId) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketOrderGoodsMapper marketOrderGoodsMapper = sqlSession.getMapper(MarketOrderGoodsMapper.class);
        MarketOrderGoodsExample marketOrderGoodsExample = new MarketOrderGoodsExample();
        marketOrderGoodsExample.or().andDeletedEqualTo(false)
                .andOrderIdEqualTo(orderId);
        List<MarketOrderGoods> orderGoodsList = marketOrderGoodsMapper.selectByExample(marketOrderGoodsExample);
        sqlSession.close();
        return orderGoodsList;
    }
}
