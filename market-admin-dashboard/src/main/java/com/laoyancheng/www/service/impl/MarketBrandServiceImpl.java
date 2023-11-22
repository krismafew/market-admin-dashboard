package com.laoyancheng.www.service.impl;

import com.github.pagehelper.PageHelper;
import com.laoyancheng.www.db.DTO.MarketBrandLabelDTO;
import com.laoyancheng.www.db.domain.MarketBrand;
import com.laoyancheng.www.db.domain.MarketBrandExample;
import com.laoyancheng.www.db.mapper.MarketBrandMapper;
import com.laoyancheng.www.service.MarketBrandService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/19 16:45
 */
public class MarketBrandServiceImpl implements MarketBrandService {
    @Override
    public List<MarketBrand> list(Integer pageNum, Integer pageSize, String sort, String order) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketBrandMapper marketBrandMapper = sqlSession.getMapper(MarketBrandMapper.class);
        MarketBrandExample marketBrandExample = new MarketBrandExample();
        marketBrandExample.orderBy(sort + " " + order).createCriteria().andDeletedEqualTo(false);
        PageHelper.startPage(pageNum, pageSize);
        List<MarketBrand> brandList = marketBrandMapper.selectByExample(marketBrandExample);
        sqlSession.close();
        return brandList;
    }

    @Override
    public MarketBrand create(MarketBrand marketBrand) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketBrandMapper marketBrandMapper = sqlSession.getMapper(MarketBrandMapper.class);
        int id = marketBrandMapper.insertSelective(marketBrand);
        sqlSession.commit();
        sqlSession.close();
        return marketBrand;
    }

    @Override
    public MarketBrand update(MarketBrand marketBrand) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketBrandMapper marketBrandMapper = sqlSession.getMapper(MarketBrandMapper.class);
        marketBrandMapper.updateByPrimaryKeySelective(marketBrand);
        sqlSession.commit();
        sqlSession.close();
        return marketBrand;
    }

    @Override
    public void delete(Integer id) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketBrandMapper marketBrandMapper = sqlSession.getMapper(MarketBrandMapper.class);
        marketBrandMapper.logicalDeleteByPrimaryKey(id);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public List<MarketBrandLabelDTO> listLabel() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketBrandMapper marketBrandMapper = sqlSession.getMapper(MarketBrandMapper.class);
        List<MarketBrandLabelDTO> brandLabelDTOList = marketBrandMapper.selectBrandLabel();
        sqlSession.close();
        return  brandLabelDTOList;
    }
}
