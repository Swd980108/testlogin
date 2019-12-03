package com.test.demo.dao;

import java.util.List;

import com.test.demo.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {

    public List<User> listUser();

    public User login(@Param("username") String username , @Param("password") String  password);

    public User existUserbyName(String username);

    public int addUser(User user);

}
