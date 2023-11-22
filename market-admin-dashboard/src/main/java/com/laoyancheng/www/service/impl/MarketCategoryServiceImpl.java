package com.laoyancheng.www.service.impl;

import com.laoyancheng.www.db.DTO.MarketCategoryDTO;
import com.laoyancheng.www.db.DTO.MarketCategoryLabelDTO;
import com.laoyancheng.www.db.domain.MarketCategory;
import com.laoyancheng.www.db.mapper.MarketCategoryMapper;
import com.laoyancheng.www.service.MarketCategoryService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
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
    public List<MarketCategoryLabelDTO> listL1() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketCategoryMapper marketCategoryMapper = sqlSession.getMapper(MarketCategoryMapper.class);
        List<MarketCategoryLabelDTO> categoryL1DTOList = marketCategoryMapper.selectLevel1Category();
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

    @Override
    public List<MarketCategoryLabelDTO> listLabel() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketCategoryMapper marketCategoryMapper = sqlSession.getMapper(MarketCategoryMapper.class);
        List<MarketCategoryLabelDTO> categoryLabelDTOList = marketCategoryMapper.selectCategoryHierarchyLabel();
        sqlSession.close();
        return  categoryLabelDTOList;
    }

    @Override
    public List<Integer> selectCategoryIdHierarchyListByL2(Integer categoryId) {
        ArrayList<Integer> idList = new ArrayList<>();
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketCategoryMapper marketCategoryMapper = sqlSession.getMapper(MarketCategoryMapper.class);
        MarketCategory marketCategory = marketCategoryMapper.selectByPrimaryKey(categoryId);
        idList.add(marketCategory.getPid());
        idList.add(categoryId);
        sqlSession.close();
        return idList;
    }
}
