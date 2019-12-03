package com.test.demo.server;


import com.test.demo.pojo.User;

import java.util.List;

public interface UserServer {

    public List<User> listUser();

    public User login(String username , String  password);

    public User existUserbyName(String username);

    public int addUser(User user);
}
