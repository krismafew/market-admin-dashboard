package com.laoyancheng.www.service.impl;

import com.laoyancheng.www.db.domain.MarketChannel;
import com.laoyancheng.www.db.mapper.MarketChannelMapper;
import com.laoyancheng.www.service.MarketChannelService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/26 21:49
 */
public class MarketChannelServiceImpl implements MarketChannelService {
    @Override
    public List<MarketChannel> list() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketChannelMapper marketChannelMapper = sqlSession.getMapper(MarketChannelMapper.class);
        List<MarketChannel> channelList = marketChannelMapper.selectAllChannel();
        sqlSession.close();
        return channelList;
    }
}
