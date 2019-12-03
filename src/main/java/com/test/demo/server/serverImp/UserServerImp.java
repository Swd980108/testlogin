package com.test.demo.server.serverImp;

import com.test.demo.dao.UserDao;
import com.test.demo.pojo.User;
import com.test.demo.server.UserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userServer")
public class UserServerImp implements UserServer {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> listUser() {
        return userDao.listUser();
    }

    @Override
    public User login(String username , String  password) {
        return userDao.login(username , password);
    }

    @Override
    public User existUserbyName(String username) {
        return userDao.existUserbyName(username);
    }

    @Override
    public int addUser(User user) {
        return userDao.addUser(user);
    }


}
