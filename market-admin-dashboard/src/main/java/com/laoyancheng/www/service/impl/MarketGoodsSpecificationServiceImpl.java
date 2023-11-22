package com.laoyancheng.www.service.impl;

import com.laoyancheng.www.db.domain.MarketGoodsSpecification;
import com.laoyancheng.www.db.domain.MarketGoodsSpecificationExample;
import com.laoyancheng.www.db.mapper.MarketGoodsSpecificationMapper;
import com.laoyancheng.www.service.MarketGoodsSpecificationService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/21 23:13
 */
public class MarketGoodsSpecificationServiceImpl implements MarketGoodsSpecificationService {
    @Override
    public void create(List<MarketGoodsSpecification> specificationList) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketGoodsSpecificationMapper specificationMapper = sqlSession.getMapper(MarketGoodsSpecificationMapper.class);
        for(MarketGoodsSpecification specification: specificationList){
            specificationMapper.insertSelective(specification);
        }
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public List<MarketGoodsSpecification> selectSpecificationListByGoodsId(Integer goodsId) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketGoodsSpecificationMapper specificationMapper = sqlSession.getMapper(MarketGoodsSpecificationMapper.class);
        MarketGoodsSpecificationExample marketGoodsSpecificationExample = new MarketGoodsSpecificationExample();
        marketGoodsSpecificationExample.or().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        List<MarketGoodsSpecification> specificationList = specificationMapper.selectByExample(marketGoodsSpecificationExample);
        sqlSession.close();
        return specificationList;
    }

    @Override
    public void updateSpecificationList(List<MarketGoodsSpecification> specificationList) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketGoodsSpecificationMapper specificationMapper = sqlSession.getMapper(MarketGoodsSpecificationMapper.class);

        for(MarketGoodsSpecification specification: specificationList){
            specification.setUpdateTime(LocalDateTime.now());
            specificationMapper.updateByPrimaryKeySelective(specification);
        }
        sqlSession.commit();
        sqlSession.close();
    }
}
