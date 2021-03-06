package com.ruanbanhai.springboot.demo.controller;

import com.ruanbanhai.springboot.demo.pojo.User;
import com.ruanbanhai.springboot.demo.service.CURDService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@EnableAutoConfiguration
public class CURDDemoController {

    @Autowired
    private CURDService curdService;

    @RequestMapping(value = "/demo", method = RequestMethod.POST)
    public void demo1(@RequestBody User user) {
        curdService.insertData(user);
    }

}
