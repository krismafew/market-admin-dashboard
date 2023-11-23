package com.laoyancheng.www.service.impl;

import com.github.pagehelper.PageHelper;
import com.laoyancheng.www.db.domain.MarketFeedback;
import com.laoyancheng.www.db.domain.MarketFeedbackExample;
import com.laoyancheng.www.db.mapper.MarketFeedbackMapper;
import com.laoyancheng.www.service.MarketFeedbackService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/19 16:22
 */
public class MarketFeedbackServiceImpl implements MarketFeedbackService {
    @Override
    public List<MarketFeedback> list(Integer pageNum, Integer pageSize, String sort, String order, String username, Integer id) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketFeedbackMapper marketFeedbackMapper = sqlSession.getMapper(MarketFeedbackMapper.class);
        MarketFeedbackExample marketFeedbackExample = new MarketFeedbackExample();
        MarketFeedbackExample.Criteria criteria = marketFeedbackExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(username))
            criteria.andUsernameLike("%" + username + "%");
        if(id != null)
            criteria.andIdEqualTo(id);
        marketFeedbackExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(pageNum, pageSize);
        List<MarketFeedback> feedbackList = marketFeedbackMapper.selectByExample(marketFeedbackExample);
        sqlSession.close();
        return feedbackList;
    }
}
