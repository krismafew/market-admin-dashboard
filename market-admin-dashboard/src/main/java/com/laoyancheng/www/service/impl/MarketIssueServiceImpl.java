package com.laoyancheng.www.service.impl;

import com.github.pagehelper.PageHelper;
import com.laoyancheng.www.db.domain.MarketIssue;
import com.laoyancheng.www.db.domain.MarketIssueExample;
import com.laoyancheng.www.db.mapper.MarketIssueMapper;
import com.laoyancheng.www.service.MarketIssueService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/21 18:06
 */
public class MarketIssueServiceImpl implements MarketIssueService {
    @Override
    public List<MarketIssue> list(Integer pageNum, Integer pageSize, String sort, String order, String question) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketIssueMapper marketIssueMapper = sqlSession.getMapper(MarketIssueMapper.class);
        MarketIssueExample marketIssueExample = new MarketIssueExample();
        MarketIssueExample.Criteria criteria = marketIssueExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(question))
            criteria.andQuestionLike("%" + question + "%");
        marketIssueExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(pageNum, pageSize);
        List<MarketIssue> issueList = marketIssueMapper.selectByExample(marketIssueExample);
        sqlSession.close();
        return issueList;
    }

    @Override
    public Object create(MarketIssue marketIssue) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketIssueMapper marketIssueMapper = sqlSession.getMapper(MarketIssueMapper.class);
        marketIssueMapper.insertSelective(marketIssue);
        sqlSession.commit();
        sqlSession.close();
        return marketIssue;
    }

    @Override
    public void update(MarketIssue marketIssue) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketIssueMapper marketIssueMapper = sqlSession.getMapper(MarketIssueMapper.class);
        marketIssueMapper.updateByPrimaryKeySelective(marketIssue);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void delete(Integer id) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketIssueMapper marketIssueMapper = sqlSession.getMapper(MarketIssueMapper.class);
        marketIssueMapper.logicalDeleteByPrimaryKey(id);
        sqlSession.commit();
        sqlSession.close();
    }
}
