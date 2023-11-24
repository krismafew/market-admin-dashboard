package com.laoyancheng.www.service.impl;

import com.laoyancheng.www.db.domain.MarketSystem;
import com.laoyancheng.www.db.domain.MarketSystemExample;
import com.laoyancheng.www.db.mapper.MarketSystemMapper;
import com.laoyancheng.www.service.MarketSystemService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;


/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/24 14:37
 */
public class MarketSystemServiceImpl implements MarketSystemService {
    @Override
    public void updateConfig(MarketSystem marketSystem) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketSystemMapper marketSystemMapper = sqlSession.getMapper(MarketSystemMapper.class);
        MarketSystemExample marketSystemExample = new MarketSystemExample();
        marketSystemExample.or().andKeyNameEqualTo(marketSystem.getKeyName());
        marketSystemMapper.updateByExampleSelective(marketSystem, marketSystemExample);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public MarketSystem getConfig(String keyName) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketSystemMapper marketSystemMapper = sqlSession.getMapper(MarketSystemMapper.class);
        MarketSystemExample marketSystemExample = new MarketSystemExample();
        marketSystemExample.or().andKeyNameEqualTo(keyName);
        MarketSystem marketSystem = marketSystemMapper.selectOneByExample(marketSystemExample);
        sqlSession.close();
        return marketSystem;
    }
}
