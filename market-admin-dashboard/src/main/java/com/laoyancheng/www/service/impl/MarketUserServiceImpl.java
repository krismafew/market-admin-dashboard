package com.laoyancheng.www.service.impl;

import com.github.pagehelper.PageHelper;
import com.laoyancheng.www.db.domain.MarketUser;
import com.laoyancheng.www.db.domain.MarketUserExample;
import com.laoyancheng.www.db.mapper.MarketUserMapper;
import com.laoyancheng.www.service.MarketUserService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @Description: 用户的Service层
 * @Author: JuRan
 * @Date: 2023/11/18 16:10
 */
public class MarketUserServiceImpl implements MarketUserService {
    @Override
    public Integer countUsers() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketUserMapper marketUserMapper = sqlSession.getMapper(MarketUserMapper.class);
        MarketUserExample marketUserExample = new MarketUserExample();
        marketUserExample.or().andDeletedEqualTo(false);
        Integer userTotal = (int)marketUserMapper.countByExample(marketUserExample);
        sqlSession.close();
        return userTotal;
    }

    @Override
    public List<MarketUser> list(Integer pageNum, Integer pageSize, String sort, String order, String username, String mobile) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketUserMapper marketUserMapper = sqlSession.getMapper(MarketUserMapper.class);
        MarketUserExample marketUserExample = new MarketUserExample();
        MarketUserExample.Criteria criteria = marketUserExample.createCriteria();
        marketUserExample.setOrderByClause(sort + " " + order);
        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(username)) {
            criteria.andUsernameLike("%" + username + "%");
        }
        if(!StringUtils.isEmpty(mobile)) {
            criteria.andMobileEqualTo(mobile);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<MarketUser> userList = marketUserMapper.selectByExample(marketUserExample);

        return userList;
    }

    @Override
    public MarketUser selectOneById(Integer id) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketUserMapper marketUserMapper = sqlSession.getMapper(MarketUserMapper.class);
        MarketUser marketUser = marketUserMapper.selectByPrimaryKey(id);
        sqlSession.close();
        return marketUser;
    }
}
