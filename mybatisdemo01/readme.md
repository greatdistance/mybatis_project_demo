# Mybaits入门

## 一、功能需求

1. 根据用户ID查询用户信息

2. 根据用户名查找用户列表

3. 添加用户

4. 修改用户

5. 删除用户

## 二、工程搭建

1. 新建maven项目，导入pom

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   
   <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
     <modelVersion>4.0.0</modelVersion>
   
     <groupId>edu.xufe</groupId>
     <artifactId>mybatisdemo01</artifactId>
     <version>1.0-SNAPSHOT</version>
     <packaging>war</packaging>
   
     <name>mybatisdemo01 Maven Webapp</name>
     <!-- FIXME change it to the project's website -->
     <url>http://www.example.com</url>
   
     <properties>
       <java.version>1.8</java.version>
     </properties>
   
     <dependencies>
       <dependency>
         <groupId>junit</groupId>
         <artifactId>junit</artifactId>
         <version>4.12</version>
       </dependency>
       <!--配置mybatis需要的依赖包-->
       <dependency>
         <groupId>asm</groupId>
         <artifactId>asm</artifactId>
         <version>3.3.1</version>
       </dependency>
   
       <dependency>
         <groupId>cglib</groupId>
         <artifactId>cglib</artifactId>
         <version>2.2.2</version>
       </dependency>
   
       <dependency>
         <groupId>commons-logging</groupId>
         <artifactId>commons-logging</artifactId>
         <version>1.1.1</version>
       </dependency>
   
       <dependency>
         <groupId>org.javassist</groupId>
         <artifactId>javassist</artifactId>
         <version>3.17.1-GA</version>
       </dependency>
   
       <dependency>
         <groupId>mysql</groupId>
         <artifactId>mysql-connector-java</artifactId>
         <version>5.1.6</version>
       </dependency>
   
       <dependency>
         <groupId>log4j</groupId>
         <artifactId>log4j</artifactId>
         <version>1.2.17</version>
       </dependency>
   
       <dependency>
         <groupId>org.slf4j</groupId>
         <artifactId>slf4j-api</artifactId>
         <version>1.7.5</version>
       </dependency>
   
       <dependency>
         <groupId>org.slf4j</groupId>
         <artifactId>slf4j-log4j12</artifactId>
         <version>1.7.5</version>
         <scope>test</scope>
       </dependency>
   
       <dependency>
         <groupId>org.mybatis</groupId>
         <artifactId>mybatis</artifactId>
         <version>3.3.0</version>
       </dependency>
     </dependencies>
   
     <build>
       <finalName>mybatisdemo01</finalName>
       <pluginManagement><!-- lock down plugins versions to avoid using Maven defaults (may be moved to parent pom) -->
         <plugins>
           <plugin>
             <artifactId>maven-clean-plugin</artifactId>
             <version>3.1.0</version>
           </plugin>
           <!-- see http://maven.apache.org/ref/current/maven-core/default-bindings.html#Plugin_bindings_for_war_packaging -->
           <plugin>
             <artifactId>maven-resources-plugin</artifactId>
             <version>3.0.2</version>
           </plugin>
           <plugin>
             <artifactId>maven-compiler-plugin</artifactId>
             <version>3.8.0</version>
           </plugin>
           <plugin>
             <artifactId>maven-surefire-plugin</artifactId>
             <version>2.22.1</version>
           </plugin>
           <plugin>
             <artifactId>maven-war-plugin</artifactId>
             <version>3.2.2</version>
           </plugin>
           <plugin>
             <artifactId>maven-install-plugin</artifactId>
             <version>2.5.2</version>
           </plugin>
           <plugin>
             <artifactId>maven-deploy-plugin</artifactId>
             <version>2.8.2</version>
           </plugin>
         </plugins>
       </pluginManagement>
     </build>
   </project>
   
   ```

2. 创建user表,随机加入数据

   ```mysql
   CREATE TABLE `user` (
     `id` int(11) NOT NULL AUTO_INCREMENT,
     `username` varchar(32) NOT NULL COMMENT '用户名称',
     `birthday` date DEFAULT NULL COMMENT '生日',
     `sex` char(1) DEFAULT NULL COMMENT '性别',
     `address` varchar(256) DEFAULT NULL COMMENT '地址',
     PRIMARY KEY (`id`)
   ) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8
   ```

   

3. 配置log4j.properties

   ```properties
   # Global logging configuration
   log4j.rootLogger=DEBUG, stdout
   # Console output...
   log4j.appender.stdout=org.apache.log4j.ConsoleAppender
   log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
   log4j.appender.stdout.layout.ConversionPattern=%5p [%t] - %m%n
   
   ```

4. 配置jdbc.properties

   ```properties
   jdbc.driver=com.mysql.jdbc.Driver
   jdbc.url=jdbc:mysql://localhost:3306/mybatis
   jdbc.username=root
   jdbc.password=mysql
   ```

   

5. 配置SqlMapConfig.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE configuration
           PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-config.dtd">
   <configuration>
       <!--加载属性文档-->
       <properties resource="jdbc.properties"></properties>
       <!--设置运行环境-->
       <environments default="development">
           <environment id="development">
               <!--事物管理-->
               <transactionManager type="JDBC"/>
               <!--数据库连接池-->
               <dataSource type="POOLED">
                   <property name="driver" value="${jdbc.driver}"/>
                   <property name="url" value="${jdbc.url}"/>
                   <property name="username" value="${jdbc.username}"/>
                   <property name="password" value="${jdbc.password}"/>
               </dataSource>
           </environment>
       </environments>
   </configuration>
   ```

6. 编写pojo

   ```java
   public class User {
       /**
        * 用户id
        */
       private Integer id;
       /**
        * 用户姓名
        */
       private String username;
   
       /**
        * 用户性别
        */
       private String sex;
   
       /**
        * 用户的生日
        */
       private Date birthday;
   
       /**
        * 用户住址
        */
       private String address;
   
       public int getId() {
           return id;
       }
   
       public void setId(Integer id) {
           this.id = id;
       }
   
       public String getUsername() {
           return username;
       }
   
       public void setUsername(String username) {
           this.username = username;
       }
   
       public String getSex() {
           return sex;
       }
   
       public void setSex(String sex) {
           this.sex = sex;
       }
   
       public Date getBirthday() {
           return birthday;
       }
   
   
       public void setBirthday(Date birthday) {
           this.birthday = birthday;
       }
   
       public String getAddress() {
           return address;
       }
   
       public void setAddress(String address) {
           this.address = address;
       }
   
       @Override
       public String toString() {
           return "User{" +
                   "id=" + id +
                   ", username='" + username + '\'' +
                   ", sex='" + sex + '\'' +
                   ", birthday=" + birthday +
                   ", address='" + address + '\'' +
                   '}';
       }
   }
   ```

## 三、完成功能需求

### 1. 步骤

1. 编写sql语句
2. 配置映射文件xml
3. 编写测试程序

### 2. 根据用户Id查询用户信息

1. 映射文件与sql，User.xml

   ```xml
   <?xml version='1.0' encoding='UTF-8'?>
   <!DOCTYPE mapper
           PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <!--namespace:命名空间，用于隔离sql语句-->
   <mapper namespace="user">
       <select id="getUserById" parameterType="int" resultType="edu.xufe.mybatis.pojo.User">
           SELECT
             `id`,
             `username`,
             `birthday`,
             `sex`,
             `address`
           FROM `user`
           WHERE id=#{id}
       </select>
   </mapper>
   ```

2.  SqlMapConfig.xml中加载映射文件

   ```xml
    <!--  需要加载 一下Users.xml文件 -->
       <mappers>
           <mapper resource="mybatis/User.xml"/>
       </mappers>
   ```

3. 编写测试程序

   ```java
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
   ```

### 3. 抽取SqlSessionFactoryUtil工具类，共享SqlSessionFactory创建过程

```java
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
```

### 4. 根据用户名查找用户列表

1. 映射文件与sql，User.xml

   ```xml
    <!--根据用户名模糊查询用户列表-->
       <select id="getUserByUsername" parameterType="string" resultType="edu.xufe.mybatis.pojo.User">
           SELECT
             `id`,
             `username`,
             `birthday`,
             `sex`,
             `address`
           FROM `user`
           WHERE username LIKE '%${value}%';
       </select>
   ```

2. SqlMapConfig.xml中加载映射文件

3. 编写测试程序

   ```java
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
   ```

### 5.添加用户

1. 映射文件与sql，User.xml

   ```xml
    <!--添加用户-->
       <insert id="insertUser" parameterType="edu.xufe.mybatis.pojo.User">
           INSERT INTO `user` (
             `username`,
             `birthday`,
             `sex`,
             `address`
           )
           VALUES
             (
               #{username},
               #{birthday},
               #{sex},
               #{address}
             ) ;
       </insert>
   ```

2. SqlMapConfig.xml中加载映射文件

3. 编写测试程序

   ```java
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
           // 执行查询
           sqlSession.insert("user.insertUser", user);
           // 提交事务
           sqlSession.commit();
           // 释放资源
           sqlSession.close();
       }
   ```

4. 自增返回

   ```xml
    <!--添加用户-->
       <!--
           selectKey： 配置主键返回
           keyProperty：要绑定pojo属性，这里只user.id
           resultType: 属性数据类型
           order: selectKey的执行时间 AFTER 之后执行  BEFORE 之前执行
       -->
   
       <!--
           useGeneratedKeys: 标识插入使用自增id
           keyProperty: 与useGeneratedKeys配套使用，用于绑定主键接收的pojo属性
       -->
       <insert id="insertUser" parameterType="edu.xufe.mybatis.pojo.User" useGeneratedKeys="true" keyProperty="id">
           <!--<selectKey keyProperty="id" resultType="int" order="AFTER">
               SELECT LAST_INSERT_ID()
           </selectKey>-->
           INSERT INTO `user` (
           `username`,
           `birthday`,
           `sex`,
           `address`
           )
           VALUES
           (
           #{username},
           #{birthday},
           #{sex},
           #{address}
           ) ;
       </insert>
   ```

   * 两种方式

   >
   >```xml
   ><!--
   >    selectKey： 配置主键返回
   >    keyProperty：要绑定pojo属性，这里只user.id
   >    resultType: 属性数据类型
   >    order: selectKey的执行时间 AFTER 之后执行  BEFORE 之前执行
   >-->
   ><selectKey keyProperty="id" resultType="int" order="AFTER">
   >    SELECT LAST_INSERT_ID()
   ></selectKey>
   >```
   >
   >```xml
   ><!--
   >    useGeneratedKeys: 标识插入使用自增id
   >    keyProperty: 与useGeneratedKeys配套使用，用于绑定主键接收的pojo属性
   >-->
   > <insert id="insertUser" parameterType="edu.xufe.mybatis.pojo.User" useGeneratedKeys="true" keyProperty="id">
   >```

5. mysql的UUID主键返回

   **<font color=red> 注：在使用uuid之前数据库user表要先加上uuid2字段、user的pojo也要加上相应属性 </font>**

   ```xml
   	<!-- useGeneratedKeys:标识插入使用自增id
   		 keyProperty:与useGeneratedKeys配套使用，用于绑定主键接收的pojo属性
   	 -->
       <insert id="insertUserUUID" parameterType="edu.xufe.mybatis.pojo.User">
           <!-- selectKey:用于配置主键返回
                keyProperty：要绑定的pojo属性
                resultType:属性数据类型
                order:指定什么时候执行，BEFORE之前
           -->
           <selectKey keyProperty="uuid2" resultType="string" order="BEFORE">
               SELECT UUID()
           </selectKey>
           INSERT INTO USER
           (
           `username`,
           `birthday`,
           `sex`,
           `address`,
           `uuid2`
           )
           VALUES (
           #{username},
           #{birthday},
           #{sex},
           #{address},
           #{uuid2});
       </insert>
   ```

### 4. 修改户名

1. 映射文件与sql，User.xml

   ```xml
   <!--更新用户-->
       <update id="updateUser" parameterType="edu.xufe.mybatis.pojo.User">
           UPDATE
             `user`
           SET
             `username` = #{username},
             `birthday` = #{birthday},
             `sex` = #{sex},
             `address` = #{address}
           WHERE `id` = #{id} ;
       </update>
   ```

2. SqlMapConfig.xml中加载映射文件

3. 编写测试程序

   ```java
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
   ```

### 4. 删除用户

1. 映射文件与sql，User.xml

   ```xml
   <!--删除用户-->
       <delete id="deleteUser" parameterType="int">
           DELETE
           FROM
             `user`
           WHERE `id` = #{id} ;
       </delete>
   ```

2. SqlMapConfig.xml中加载映射文件

3. 编写测试程序

   ```java
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
   ```

## 四、入门总结

1. Mybatis架构图

   ![Mybatis架构图](https://raw.githubusercontent.com/greatdistance/mybatis_project_demo/master/mybatisdemo01/image/mybatis_structure_chart.jpg)