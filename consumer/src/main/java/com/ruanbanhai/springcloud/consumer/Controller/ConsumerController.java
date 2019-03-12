package com.ruanbanhai.springcloud.consumer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/consumer",method = RequestMethod.GET)
    public String consumerTest(){
        ResponseEntity<String> entity = restTemplate.getForEntity("http://provider:9001/provider", String.class);
        String body = entity.getBody();
        return body;
    }
}
