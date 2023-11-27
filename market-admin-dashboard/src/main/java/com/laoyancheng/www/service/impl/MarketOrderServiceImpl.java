package com.laoyancheng.www.service.impl;

import com.github.pagehelper.PageHelper;
import com.laoyancheng.www.constant.MarketOrderStatusConstants;
import com.laoyancheng.www.db.domain.MarketOrder;
import com.laoyancheng.www.db.domain.MarketOrderExample;
import com.laoyancheng.www.db.mapper.MarketOrderMapper;
import com.laoyancheng.www.service.MarketOrderService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 商品订单的Service层
 * @Author: JuRan
 * @Date: 2023/11/18 16:15
 */
public class MarketOrderServiceImpl implements MarketOrderService {
    @Override
    public Integer countOrders() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketOrderMapper marketOrderMapper = sqlSession.getMapper(MarketOrderMapper.class);
        MarketOrderExample marketOrderExample = new MarketOrderExample();
        marketOrderExample.or().andDeletedEqualTo(false);
        Integer orderTotal = (int)marketOrderMapper.countByExample(marketOrderExample);
        sqlSession.close();
        return orderTotal;
    }

    @Override
    public List<MarketOrder> list(Integer pageNum, Integer pageSize, String sort, String order, Integer userId, String orderSn, LocalDateTime start, LocalDateTime end, ArrayList<Short> statusList) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketOrderMapper marketOrderMapper = sqlSession.getMapper(MarketOrderMapper.class);
        MarketOrderExample marketOrderExample = new MarketOrderExample();
        MarketOrderExample.Criteria criteria = marketOrderExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if(userId != null)
            criteria.andUserIdEqualTo(userId);
        if(!StringUtils.isEmpty(orderSn))
            criteria.andOrderSnEqualTo(orderSn);
        if(start != null)
            criteria.andAddTimeGreaterThanOrEqualTo(start);
        if(end != null)
            criteria.andAddTimeLessThanOrEqualTo(end);

        if(statusList.size() != 0){
            criteria.andOrderStatusIn(statusList);
        }
        marketOrderExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(pageNum, pageSize);
        List<MarketOrder> orderList = marketOrderMapper.selectByExample(marketOrderExample);
        sqlSession.close();
        return orderList;
    }

    @Override
    public MarketOrder selectOrderById(Integer id) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketOrderMapper marketOrderMapper = sqlSession.getMapper(MarketOrderMapper.class);
        MarketOrder order = marketOrderMapper.selectByPrimaryKeySelective(id);
        sqlSession.close();
        return order;
    }

    @Override
    public void update(MarketOrder marketOrder) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketOrderMapper marketOrderMapper = sqlSession.getMapper(MarketOrderMapper.class);
        marketOrderMapper.updateByPrimaryKeySelective(marketOrder);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void delete(Integer orderId) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketOrderMapper marketOrderMapper = sqlSession.getMapper(MarketOrderMapper.class);
        marketOrderMapper.logicalDeleteByPrimaryKey(orderId);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public List<MarketOrder> selectPaidOrderListByTime(LocalDateTime start, LocalDateTime end) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketOrderMapper marketOrderMapper = sqlSession.getMapper(MarketOrderMapper.class);
        MarketOrderExample marketOrderExample = new MarketOrderExample();
        marketOrderExample.or().andDeletedEqualTo(false)
                .andAddTimeGreaterThanOrEqualTo(start)
                .andAddTimeLessThanOrEqualTo(end)
                        .andOrderStatusIn(Arrays.asList(new Short[]{MarketOrderStatusConstants.PAID, MarketOrderStatusConstants.SHIPPED, MarketOrderStatusConstants.REFUND_APPLIED,
                        MarketOrderStatusConstants.SYSTEM_CONFIRMED, MarketOrderStatusConstants.USER_CONFIRMED}));
        List<MarketOrder> orderList = marketOrderMapper.selectByExample(marketOrderExample);
        sqlSession.close();
        return orderList;
    }
}
