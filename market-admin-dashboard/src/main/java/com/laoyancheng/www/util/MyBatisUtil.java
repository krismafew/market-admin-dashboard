package com.laoyancheng.www.util;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;


public class MyBatisUtil {
    private static SqlSessionFactory sqlSessionFactory;

    static{
        ClassLoader classLoader = MyBatisUtil.class.getClassLoader();
        InputStream stream = classLoader.getResourceAsStream("mybatis.xml");
         sqlSessionFactory = new SqlSessionFactoryBuilder().build(stream);
    }

    public static SqlSession getSqlSession(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession;
    }
}
