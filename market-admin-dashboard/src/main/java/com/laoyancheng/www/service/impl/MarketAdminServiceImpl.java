package com.laoyancheng.www.service.impl;

import com.github.pagehelper.PageHelper;
import com.laoyancheng.www.db.domain.MarketAdmin;
import com.laoyancheng.www.db.domain.MarketAdminExample;
import com.laoyancheng.www.db.domain.MarketRole;
import com.laoyancheng.www.db.mapper.MarketAdminMapper;
import com.laoyancheng.www.db.mapper.MarketRoleMapper;
import com.laoyancheng.www.service.MarketAdminService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import java.time.LocalDateTime;
import java.util.List;

public class MarketAdminServiceImpl implements MarketAdminService {

    @Override
    public MarketAdmin login(String username, String password, String localAddr) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketAdminMapper adminMapper = sqlSession.getMapper(MarketAdminMapper.class);
        // 查询数据库中有没有用户输入的密码和账号匹配的数据行
        MarketAdminExample adminExample = new MarketAdminExample();
        adminExample.or().andUsernameEqualTo(username).andPasswordEqualTo(password);
        // 通过mapper查询数据库，并且orm封装成对象
        MarketAdmin marketAdmin = adminMapper.selectOneByExample(adminExample);
        // 如果admin不是null, 更新last_login_ip和last_login_time
        if(marketAdmin != null){
            marketAdmin.setLastLoginIp(localAddr);
            marketAdmin.setLastLoginTime(LocalDateTime.now());
        }
        // 关闭SqlSession
        sqlSession.close();
        return marketAdmin;
    }

    @Override
    public List<MarketAdmin> list(Integer pageNum, Integer pageSize, String sort, String order, String username) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketAdminMapper marketAdminMapper = sqlSession.getMapper(MarketAdminMapper.class);
        MarketAdminExample adminExample = new MarketAdminExample();
        MarketAdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(username))
            criteria.andUsernameLike("%" + username + "%");
        adminExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(pageNum, pageSize);
        List<MarketAdmin> adminList = marketAdminMapper.selectByExample(adminExample);
        sqlSession.close();
        return adminList;
    }

    @Override
    public MarketAdmin create(MarketAdmin marketAdmin) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketAdminMapper marketAdminMapper = sqlSession.getMapper(MarketAdminMapper.class);
        marketAdminMapper.insertSelective(marketAdmin);
        sqlSession.commit();
        sqlSession.close();
        return marketAdmin;
    }

    @Override
    public void update(MarketAdmin marketAdmin) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketAdminMapper adminMapper = sqlSession.getMapper(MarketAdminMapper.class);
        adminMapper.updateByPrimaryKeySelective(marketAdmin);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void delete(Integer id) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        MarketAdminMapper marketAdminMapper = sqlSession.getMapper(MarketAdminMapper.class);
        marketAdminMapper.logicalDeleteByPrimaryKey(id);
        sqlSession.commit();
        sqlSession.close();
    }
}
