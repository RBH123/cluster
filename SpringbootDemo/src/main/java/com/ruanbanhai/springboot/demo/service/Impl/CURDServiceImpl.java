package com.ruanbanhai.springboot.demo.service.Impl;//package com.ruanbanhai.springboot.demo.service.CURDServiceImpl;

import com.ruanbanhai.springboot.demo.dao.UserMapper;
import com.ruanbanhai.springboot.demo.pojo.User;
import com.ruanbanhai.springboot.demo.service.CURDService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CURDServiceImpl implements CURDService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public void insertData(User user) {
        userMapper.insert(user);
    }
}
