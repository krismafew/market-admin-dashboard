package com.laoyancheng.www.service.impl;

import com.github.pagehelper.PageHelper;
import com.laoyancheng.www.db.domain.MarketLog;
import com.laoyancheng.www.db.domain.MarketLogExample;
import com.laoyancheng.www.db.mapper.MarketLogMapper;
import com.laoyancheng.www.service.MarketLogService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/23 23:20
 */
public class MarketLogServiceImpl implements MarketLogService {
    @Override
    public List<MarketLog> list(Integer pageNum, Integer pageSize, String sort, String order, String name) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketLogMapper marketLogMapper = sqlSession.getMapper(MarketLogMapper.class);
        MarketLogExample marketLogExample = new MarketLogExample();
        MarketLogExample.Criteria criteria = marketLogExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(name))
            criteria.andAdminLike("%" + name + "%");
        marketLogExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(pageNum, pageSize);
        List<MarketLog> logList = marketLogMapper.selectByExample(marketLogExample);
        sqlSession.close();
        return logList;
    }
}
