package com.laoyancheng.www.service.impl;

import com.laoyancheng.www.db.DTO.MarketCategoryDTO;
import com.laoyancheng.www.db.DTO.MarketCategoryL1DTO;
import com.laoyancheng.www.db.domain.MarketCategory;
import com.laoyancheng.www.db.mapper.MarketCategoryMapper;
import com.laoyancheng.www.service.MarketCategoryService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/20 22:01
 */
public class MarketCategoryServiceImpl implements MarketCategoryService {
    @Override
    public List<MarketCategoryDTO> list() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketCategoryMapper marketCategoryMapper = sqlSession.getMapper(MarketCategoryMapper.class);
        List<MarketCategoryDTO> list = marketCategoryMapper.selectCategoryHierarchy();
        sqlSession.close();
        return list;
    }

    @Override
    public List<MarketCategoryL1DTO> listL1() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketCategoryMapper marketCategoryMapper = sqlSession.getMapper(MarketCategoryMapper.class);
        List<MarketCategoryL1DTO> categoryL1DTOList = marketCategoryMapper.selectLevel1Category();
        sqlSession.close();
        return categoryL1DTOList;
    }

    @Override
    public Object create(MarketCategory marketCategory) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketCategoryMapper marketCategoryMapper = sqlSession.getMapper(MarketCategoryMapper.class);
        marketCategoryMapper.insertSelective(marketCategory);
        sqlSession.commit();
        sqlSession.close();
        return marketCategory;
    }

    @Override
    public void update(MarketCategory marketCategory) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketCategoryMapper marketCategoryMapper = sqlSession.getMapper(MarketCategoryMapper.class);
        marketCategoryMapper.updateByPrimaryKeySelective(marketCategory);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void delete(Integer id) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketCategoryMapper marketCategoryMapper = sqlSession.getMapper(MarketCategoryMapper.class);
        marketCategoryMapper.logicalDeleteByPrimaryKey(id);
        sqlSession.commit();
        sqlSession.close();
    }
}
