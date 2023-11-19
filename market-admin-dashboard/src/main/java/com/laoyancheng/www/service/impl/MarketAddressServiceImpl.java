package com.laoyancheng.www.service.impl;

import com.github.pagehelper.PageHelper;
import com.laoyancheng.www.db.domain.MarketAddress;
import com.laoyancheng.www.db.domain.MarketAddressExample;
import com.laoyancheng.www.db.mapper.MarketAddressMapper;
import com.laoyancheng.www.service.MarketAddressService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/19 14:43
 */
public class MarketAddressServiceImpl implements MarketAddressService {
    @Override
    public List<MarketAddress> list(Integer pageNum, Integer pageSize, String sort, String order) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketAddressMapper marketAddressMapper = sqlSession.getMapper(MarketAddressMapper.class);
        MarketAddressExample marketAddressExample = new MarketAddressExample();
        marketAddressExample.orderBy(sort + " " + order).createCriteria().andDeletedEqualTo(false);
        PageHelper.startPage(pageNum, pageSize);
        List<MarketAddress> addressList = marketAddressMapper.selectByExample(marketAddressExample);
        sqlSession.close();
        return addressList;
    }
}
