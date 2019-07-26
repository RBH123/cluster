package com.ruanbanhai.springboot.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;

@Slf4j
public class Producer implements ApplicationContextAware {

    private JedisCluster jedisCluster;
    private String producerName;

    public Producer(String producerName) {
        this.producerName = producerName;
        init();
    }

    public void init() {
        this.jedisCluster = (JedisCluster) SpringBootUtils.getBean("jedisCluster");
    }

    public void putMessage(String queue, String message) throws Exception {
        System.out.println(jedisCluster);
        if (StringUtils.isNotEmpty(queue) && StringUtils.isNotEmpty(message)) {
            if (jedisCluster != null) {
                jedisCluster.lpush(queue, message);
                System.out.println("消息发送成功");
            }
        } else {
            throw new Exception("入参错误");
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
