package com.laoyancheng.www.service.impl;

import com.laoyancheng.www.db.DTO.MarketRegionDTO;
import com.laoyancheng.www.db.mapper.MarketRegionMapper;
import com.laoyancheng.www.service.MarketRegionService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/25 10:46
 */
public class MarketRegionServiceImpl implements MarketRegionService {
    @Override
    public List<MarketRegionDTO> list() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketRegionMapper marketRegionMapper = sqlSession.getMapper(MarketRegionMapper.class);
        List<MarketRegionDTO> regionDTOList = marketRegionMapper.selectRegionHierarchy();
        sqlSession.close();
        return regionDTOList;
    }
}
