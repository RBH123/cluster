package com.ruanbanhai.springboot.demo.service.Impl;
import com.ruanbanhai.springboot.demo.pojo.User;
import com.ruanbanhai.springboot.demo.service.CURDService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class CURDServiceImpl implements CURDService{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void insertData(User user) {
        User insert = mongoTemplate.insert(user);
        System.out.println(insert.toString());
    }

    @Override
    public List<User> getUserData() {
        List<User> userList = mongoTemplate.findAll(User.class);
        userList.forEach(user -> {
            System.out.println(user);
        });
        return null;
    }
}
