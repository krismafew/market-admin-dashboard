package com.laoyancheng.www.service.impl;

import com.laoyancheng.www.db.domain.MarketGoodsProductExample;
import com.laoyancheng.www.db.mapper.MarketGoodsProductMapper;
import com.laoyancheng.www.service.MarketGoodsProductService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 * @Description: GoodsProduct的Service层
 * @Author: JuRan
 * @Date: 2023/11/18 16:13
 */
public class MarketGoodsProductServiceImpl implements MarketGoodsProductService {
    @Override
    public Integer countProducts() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketGoodsProductMapper marketGoodsProductMapper = sqlSession.getMapper(MarketGoodsProductMapper.class);
        MarketGoodsProductExample marketGoodsProductExample = new MarketGoodsProductExample();
        marketGoodsProductExample.or().andDeletedEqualTo(false);
        Integer productTotal = (int)marketGoodsProductMapper.countByExample(marketGoodsProductExample);
        sqlSession.close();
        return productTotal;
    }
}
