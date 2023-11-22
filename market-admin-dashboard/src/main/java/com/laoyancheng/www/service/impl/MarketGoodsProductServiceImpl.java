package com.laoyancheng.www.service.impl;

import com.laoyancheng.www.db.domain.MarketGoodsProduct;
import com.laoyancheng.www.db.domain.MarketGoodsProductExample;
import com.laoyancheng.www.db.mapper.MarketGoodsProductMapper;
import com.laoyancheng.www.service.MarketGoodsProductService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public void create(List<MarketGoodsProduct> productList) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketGoodsProductMapper marketGoodsProductMapper = sqlSession.getMapper(MarketGoodsProductMapper.class);
        for(MarketGoodsProduct product : productList){
            marketGoodsProductMapper.insertSelective(product);
        }
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public List<MarketGoodsProduct> selectProductListByGoodsId(Integer goodsId) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketGoodsProductMapper marketGoodsProductMapper = sqlSession.getMapper(MarketGoodsProductMapper.class);
        MarketGoodsProductExample marketGoodsProductExample = new MarketGoodsProductExample();
        marketGoodsProductExample.or().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        List<MarketGoodsProduct> productList = marketGoodsProductMapper.selectByExample(marketGoodsProductExample);
        sqlSession.close();
        return productList;
    }

    @Override
    public void updateProductList(List<MarketGoodsProduct> productList) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketGoodsProductMapper marketGoodsProductMapper = sqlSession.getMapper(MarketGoodsProductMapper.class);

        for(MarketGoodsProduct product: productList){
            product.setUpdateTime(LocalDateTime.now());
            marketGoodsProductMapper.updateByPrimaryKeySelective(product);
        }

        sqlSession.commit();
        sqlSession.close();
    }
}
