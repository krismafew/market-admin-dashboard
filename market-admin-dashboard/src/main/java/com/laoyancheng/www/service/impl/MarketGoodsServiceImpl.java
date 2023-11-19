package com.laoyancheng.www.service.impl;

import com.laoyancheng.www.db.domain.MarketGoodsExample;
import com.laoyancheng.www.db.mapper.MarketGoodsMapper;
import com.laoyancheng.www.service.MarketGoodsService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 * @Description: 商品的Service层
 * @Author: JuRan
 * @Date: 2023/11/18 16:12
 */
public class MarketGoodsServiceImpl implements MarketGoodsService {
    @Override
    public Integer countGoods() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketGoodsMapper goodsMapper = sqlSession.getMapper(MarketGoodsMapper.class);
        MarketGoodsExample goodsExample = new MarketGoodsExample();
        goodsExample.or().andDeletedEqualTo(false);
        Integer goodsTotal = (int)goodsMapper.countByExample(goodsExample);
        sqlSession.close();
        return goodsTotal;

    }
}
