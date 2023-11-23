package com.laoyancheng.www.service.impl;

import com.github.pagehelper.PageHelper;
import com.laoyancheng.www.db.domain.MarketStorage;
import com.laoyancheng.www.db.domain.MarketStorageExample;
import com.laoyancheng.www.db.mapper.MarketStorageMapper;
import com.laoyancheng.www.service.MarketStorageService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

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

    @Override
    public List<MarketStorage> list(Integer pageNum, Integer pageSize, String sort, String order, String key, String name) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketStorageMapper marketStorageMapper = sqlSession.getMapper(MarketStorageMapper.class);
        MarketStorageExample marketStorageExample = new MarketStorageExample();
        MarketStorageExample.Criteria criteria = marketStorageExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(key))
            criteria.andKeyEqualTo(key);
        if(!StringUtils.isEmpty(name))
            criteria.andNameLike("%" + name + "%");
        marketStorageExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(pageNum, pageSize);
        List<MarketStorage> storageList = marketStorageMapper.selectByExample(marketStorageExample);
        sqlSession.close();
        return storageList;
    }

    @Override
    public void update(MarketStorage marketStorage) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketStorageMapper marketStorageMapper = sqlSession.getMapper(MarketStorageMapper.class);
        marketStorageMapper.updateByPrimaryKeySelective(marketStorage);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void delete(Integer id) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketStorageMapper marketStorageMapper = sqlSession.getMapper(MarketStorageMapper.class);
        marketStorageMapper.logicalDeleteByPrimaryKey(id);
        sqlSession.commit();
        sqlSession.close();
    }
}
