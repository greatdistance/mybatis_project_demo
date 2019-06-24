package edu.xufe.mybatis.pojo;

import java.util.Date;

/**
 * @ClassName User
 * @Description 数据库user表对应的pojo类
 * @Author greatDistance
 * @Date 2019/4/19 11:01
 * @Version 1.0
 */
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
