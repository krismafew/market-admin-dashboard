package com.laoyancheng.www.service.impl;

import com.github.pagehelper.PageHelper;
import com.laoyancheng.www.db.domain.MarketFootprint;
import com.laoyancheng.www.db.domain.MarketFootprintExample;
import com.laoyancheng.www.db.mapper.MarketFootprintMapper;
import com.laoyancheng.www.service.MarketFootprintService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/19 15:41
 */
public class MarketFootprintServiceImpl implements MarketFootprintService {

    @Override
    public List<MarketFootprint> list(Integer pageNum, Integer pageSize, String sort, String order, Integer userId, Integer goodsId) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketFootprintMapper marketFootprintMapper = sqlSession.getMapper(MarketFootprintMapper.class);
        MarketFootprintExample marketFootprintExample = new MarketFootprintExample();
        MarketFootprintExample.Criteria criteria = marketFootprintExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if(userId != null)
            criteria.andUserIdEqualTo(userId);
        if(goodsId != null)
            criteria.andGoodsIdEqualTo(goodsId);
        marketFootprintExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(pageNum, pageSize);
        List<MarketFootprint> footprintList = marketFootprintMapper.selectByExample(marketFootprintExample);
        sqlSession.close();
        return footprintList;
    }
}
