package edu.xufe.mybatis.pojo;

import edu.xufe.mybatis.utils.SqlSessionFactoryUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class UserTest {
    @Test
    public void testGetUserById() throws Exception {
        // 1.创建SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // 2.创建核心配置文件的输入流
        InputStream inputStream = inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        // 3.通过输入流创建SqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
        // 4.创建SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 5.执行查询
        User user = sqlSession.selectOne("user.getUserById", 1);
        System.out.println("user = " + user);
        // 6.释放资源
        sqlSession.close();
    }

    @Test
    public void testGetUserByUsername() {
        // 获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();
        // 创建SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 执行查询
        List<User> list = sqlSession.selectList("user.getUserByUsername", "余");
        Iterator<User> userIterator = list.iterator();
        while (userIterator.hasNext()) {
            System.out.println("user = " + userIterator.next());
        }
        // 释放资源
        sqlSession.close();
    }

    @Test
    public void testInsertUser() {
        // 获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();
        // 创建SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = new User();
        user.setSex("男");
        user.setUsername("余永涛");
        user.setAddress("西安财经大学_1B207-3");
        user.setBirthday(new Date());
        System.out.println("执行前==>user = " + user);
        // 执行查询
        sqlSession.insert("user.insertUser", user);
        // 提交事务
        sqlSession.commit();
        System.out.println("执行后==>user = " + user);
        // 释放资源
        sqlSession.close();
    }

    @Test
    public void testUpdateUser() {
        // 获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();
        // 创建SqlSession 自动提交事务
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        // 执行 查询
        User user = sqlSession.selectOne("user.getUserById", 1);
        user.setBirthday(new Date());
        user.setAddress("西安东软");
        // 执行 修改
        sqlSession.update("user.updateUser", user);
        // 释放资源
        sqlSession.close();
    }

    @Test
    public void testDeleteUser() {
        // 获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();
        // 创建SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        // 执行删除
        sqlSession.delete("user.deleteUser", 42);
        //释放资源
        sqlSession.close();
    }

}

