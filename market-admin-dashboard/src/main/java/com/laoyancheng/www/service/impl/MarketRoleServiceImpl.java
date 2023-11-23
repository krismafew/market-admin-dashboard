package com.laoyancheng.www.service.impl;

import com.laoyancheng.www.db.DTO.MarketRoleDTO;
import com.laoyancheng.www.db.mapper.MarketRoleMapper;
import com.laoyancheng.www.service.MarketRoleService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/23 17:01
 */
public class MarketRoleServiceImpl implements MarketRoleService {
    @Override
    public List<MarketRoleDTO> listAdminRoleLabel() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketRoleMapper marketRoleMapper = sqlSession.getMapper(MarketRoleMapper.class);
        List<MarketRoleDTO> roleDTOList = marketRoleMapper.selectLabelList();
        sqlSession.close();
        return roleDTOList;
    }
}
