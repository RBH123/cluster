package com.springcloud.consumers.Controller;

import com.springcloud.consumers.Service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    @RequestMapping(value = "/consumer")
    public String consumerTest(){
        String consumers = consumerService.consumers();
        return consumers;
    }
}
