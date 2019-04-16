package com.ruanbanhai.springboot.demo.service.impl;

import com.ruanbanhai.springboot.demo.dao.UserDao;
import com.ruanbanhai.springboot.demo.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * user serverImpl
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {


    /**
     * 注入dao
     */
    @Resource
    private UserDao userDao;

    /**
     * 初始化
     */
    @Override
    public UserDao initDao() {
        return userDao;
    }


}
