package com.ruanbanhai.springboot.demo.controller;

import com.ruanbanhai.springboot.demo.pojo.Goods;
import com.ruanbanhai.springboot.demo.pojo.User;
import com.ruanbanhai.springboot.demo.service.CURDService;
import com.ruanbanhai.springboot.demo.service.MessageProvider;
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

    @Autowired
    private MessageProvider messageProvider;

    @RequestMapping(value = "/demo", method = RequestMethod.POST)
    public void demo1(@RequestBody User user) {
//        System.out.println(user.getUsername());
//        curdService.insertData(user);
        messageProvider.push(user);
    }

    @RequestMapping(value = "/push", method = RequestMethod.POST)
    public void demo2(@RequestBody Goods goods) {
        log.info("goods:{}", goods);
        messageProvider.push(goods);
    }
}
