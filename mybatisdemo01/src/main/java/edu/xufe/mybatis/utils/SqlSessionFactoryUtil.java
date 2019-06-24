package edu.xufe.mybatis.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName SqlSessionFactoryUtil
 * @Description SqlSessionFactory工具类
 * @Author greatDistance
 * @Date 2019/4/19 12:06
 * @Version 1.0
 */
public class SqlSessionFactoryUtil {
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            // 配置文件名称
            String resource = "SqlMapConfig.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            // 使用SqlSessionFactoryBuilder从xml配置文件中创建SqlSessionFactory
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取SqlSessionFactory
     *
     * @return SqlSessionFactory
     */
    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
