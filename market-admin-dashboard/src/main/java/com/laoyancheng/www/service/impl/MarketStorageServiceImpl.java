package com.laoyancheng.www.service.impl;

import com.laoyancheng.www.db.domain.MarketStorage;
import com.laoyancheng.www.db.domain.MarketStorageExample;
import com.laoyancheng.www.db.mapper.MarketStorageMapper;
import com.laoyancheng.www.service.MarketStorageService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/22 16:20
 */
public class MarketStorageServiceImpl implements MarketStorageService {
    @Override
    public MarketStorage create(MarketStorage marketStorage) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketStorageMapper storageMapper = sqlSession.getMapper(MarketStorageMapper.class);
        storageMapper.insertSelective(marketStorage);
        sqlSession.commit();
        sqlSession.close();
        return marketStorage;
    }

    @Override
    public MarketStorage selectStorageByKey(String key) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketStorageMapper marketStorageMapper = sqlSession.getMapper(MarketStorageMapper.class);
        MarketStorageExample marketStorageExample = new MarketStorageExample();
        marketStorageExample.or().andKeyEqualTo(key);
        MarketStorage marketStorage = marketStorageMapper.selectOneByExample(marketStorageExample);
        sqlSession.close();
        return marketStorage;
    }
}
