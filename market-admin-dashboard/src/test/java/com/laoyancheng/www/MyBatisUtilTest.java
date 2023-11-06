package com.laoyancheng.www;

import com.laoyancheng.www.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class MyBatisUtilTest {
    @Test
    public void testMyBatisUtil(){
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        sqlSession.close();
    }
}
