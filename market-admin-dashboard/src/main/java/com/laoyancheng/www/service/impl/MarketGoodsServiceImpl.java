package com.laoyancheng.www.service.impl;

import com.github.pagehelper.PageHelper;
import com.laoyancheng.www.db.domain.MarketGoods;
import com.laoyancheng.www.db.domain.MarketGoodsExample;
import com.laoyancheng.www.db.mapper.MarketGoodsMapper;
import com.laoyancheng.www.service.MarketGoodsService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public List<MarketGoods> list(Integer pageNum, Integer pageSize, String sort, String order, String goodsSn, String name, Integer goodsId) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketGoodsMapper marketGoodsMapper = sqlSession.getMapper(MarketGoodsMapper.class);
        MarketGoodsExample goodsExample = new MarketGoodsExample();
        MarketGoodsExample.Criteria criteria = goodsExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(goodsSn))
            criteria.andGoodsSnEqualTo(goodsSn);
        if(goodsId != null)
            criteria.andIdEqualTo(goodsId);
        if(!StringUtils.isEmpty(name))
            criteria.andNameLike("%" + name + "%");
        goodsExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(pageNum, pageSize);
        List<MarketGoods> goodsList = marketGoodsMapper.selectByExample(goodsExample);
        sqlSession.close();
        return goodsList;
    }

    @Override
    public Integer create(MarketGoods marketGoods) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketGoodsMapper goodsMapper = sqlSession.getMapper(MarketGoodsMapper.class);
        goodsMapper.insertSelective(marketGoods);
        sqlSession.commit();
        sqlSession.close();
        return marketGoods.getId();
    }

    @Override
    public void delete(Integer id) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketGoodsMapper goodsMapper = sqlSession.getMapper(MarketGoodsMapper.class);
        goodsMapper.logicalDeleteByPrimaryKey(id);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public MarketGoods selectGoodsById(Integer goodsId) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketGoodsMapper goodsMapper = sqlSession.getMapper(MarketGoodsMapper.class);
        MarketGoods marketGoods = goodsMapper.selectByPrimaryKey(goodsId);
        sqlSession.close();
        return marketGoods;
    }

    @Override
    public void updateGoods(MarketGoods marketGoods) {
        marketGoods.setUpdateTime(LocalDateTime.now());
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketGoodsMapper goodsMapper = sqlSession.getMapper(MarketGoodsMapper.class);
        goodsMapper.updateByPrimaryKeySelective(marketGoods);
        sqlSession.commit();
        sqlSession.close();
    }
}
