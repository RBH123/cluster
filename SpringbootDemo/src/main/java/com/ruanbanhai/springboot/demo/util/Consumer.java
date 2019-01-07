package com.ruanbanhai.springboot.demo.util;


import redis.clients.jedis.JedisCluster;

import java.util.List;

public class Consumer {

    private String consumerName;
    private JedisCluster jedisCluster;

    public Consumer(String consumerName) {
        this.consumerName = consumerName;
        init();
    }

    public void init() {
        jedisCluster = (JedisCluster) SpringBootUtils.getBean("jedisCluster");
    }

    public String receiveMessage(String queue) {
//       if(jedisCluster != null){
//           String rpop = jedisCluster.rpop(queue);
//           return rpop;
//       }
//       return null;
//    }
        List<String> brpop = jedisCluster.brpop(0, queue);
        if (brpop != null) {
            for (int i = 0; i < brpop.size(); i++) {
                String s = brpop.get(i);
                System.out.println(i + ":" + s);
            }
        }
        return null;
    }
}
