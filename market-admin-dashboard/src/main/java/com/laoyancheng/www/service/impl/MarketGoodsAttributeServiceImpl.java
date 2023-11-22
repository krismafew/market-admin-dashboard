package com.laoyancheng.www.service.impl;

import com.laoyancheng.www.db.domain.MarketGoodsAttribute;
import com.laoyancheng.www.db.domain.MarketGoodsAttributeExample;
import com.laoyancheng.www.db.mapper.MarketGoodsAttributeMapper;
import com.laoyancheng.www.service.MarketGoodsAttributeService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/21 22:54
 */
public class MarketGoodsAttributeServiceImpl implements MarketGoodsAttributeService {
    @Override
    public void create(List<MarketGoodsAttribute> attributeList) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketGoodsAttributeMapper attributeMapper = sqlSession.getMapper(MarketGoodsAttributeMapper.class);
        for(MarketGoodsAttribute attribute: attributeList){
            attributeMapper.insertSelective(attribute);
        }
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public List<MarketGoodsAttribute> selectAttributeListByGoodsId(Integer goodsId) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketGoodsAttributeMapper attributeMapper = sqlSession.getMapper(MarketGoodsAttributeMapper.class);
        MarketGoodsAttributeExample marketGoodsAttributeExample = new MarketGoodsAttributeExample();
        marketGoodsAttributeExample.or().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        List<MarketGoodsAttribute> attributeList = attributeMapper.selectByExample(marketGoodsAttributeExample);
        sqlSession.close();
        return attributeList;
    }

    @Override
    public void updateAttributeList(List<MarketGoodsAttribute> attributeList) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketGoodsAttributeMapper attributeMapper = sqlSession.getMapper(MarketGoodsAttributeMapper.class);

        for(MarketGoodsAttribute attribute: attributeList){
            attribute.setUpdateTime(LocalDateTime.now());
            attributeMapper.updateByPrimaryKeySelective(attribute);
        }

        sqlSession.commit();
        sqlSession.close();
    }
}
