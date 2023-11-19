package com.laoyancheng.www.service.impl;

import com.laoyancheng.www.db.domain.MarketAdmin;
import com.laoyancheng.www.db.domain.MarketAdminExample;
import com.laoyancheng.www.db.mapper.MarketAdminMapper;
import com.laoyancheng.www.service.MarketAdminService;
import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

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
            // TODO: 更新用户最新登陆时间

        }
        // 关闭SqlSession
        sqlSession.close();
        return marketAdmin;
    }
}
