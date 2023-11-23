package com.laoyancheng.www.service.impl;

import com.github.pagehelper.PageHelper;
import com.laoyancheng.www.db.domain.MarketComment;
import com.laoyancheng.www.db.domain.MarketCommentExample;
import com.laoyancheng.www.db.mapper.MarketCommentMapper;
import com.laoyancheng.www.service.MarketCommentService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/22 11:32
 */
public class MarketCommentServiceImpl implements MarketCommentService {
    @Override
    public List<MarketComment> list(Integer pageNum, Integer pageSize, String sort, String order, Integer userId, Integer valueId) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketCommentMapper marketCommentMapper = sqlSession.getMapper(MarketCommentMapper.class);
        MarketCommentExample marketCommentExample = new MarketCommentExample();
        MarketCommentExample.Criteria criteria = marketCommentExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if(userId != null)
            criteria.andUserIdEqualTo(userId);
        if(valueId != null)
            criteria.andValueIdEqualTo(valueId);
        marketCommentExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(pageNum, pageSize);
        List<MarketComment> commentList = marketCommentMapper.selectByExample(marketCommentExample);
        sqlSession.close();
        return commentList;
    }

    @Override
    public void delete(Integer id) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketCommentMapper marketCommentMapper = sqlSession.getMapper(MarketCommentMapper.class);
        marketCommentMapper.logicalDeleteByPrimaryKey(id);
        sqlSession.commit();
        sqlSession.close();
    }
}
