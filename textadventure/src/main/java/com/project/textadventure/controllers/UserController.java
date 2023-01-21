package com.project.textadventure.controllers;

import com.project.textadventure.dao.User;
import com.project.textadventure.dao.UserDao;
import com.project.textadventure.dao.UserDaoImpl;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class UserController {
    @Resource
    UserDao userDao;

    public List<User> findAll() {
        return userDao.findAll();
    }

    public void insertUser(User user) {
        userDao.insertUser(user);
    }

    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    public void executeUpdateUser(User user) {
        userDao.executeUpdateUser(user);
    }

    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }
}
