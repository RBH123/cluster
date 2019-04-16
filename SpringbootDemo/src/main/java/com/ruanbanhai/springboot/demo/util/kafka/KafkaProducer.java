package com.ruanbanhai.springboot.demo.util.kafka;

import com.alibaba.fastjson.JSON;
import com.ruanbanhai.springboot.demo.pojo.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void send() {
        Goods goods = new Goods();
        goods.setId(18);
        for (int i = 0; i < 2; i++) {
            goods.setId(30 + i);
            goods.setGoodsDescription("中国制造");
            goods.setGoodsName("华为mate20");
            kafkaTemplate.send("msg", i, "msg-" + i, JSON.toJSONString(goods));
//            TopicPartition topicPartition = new TopicPartition("message",2);
////            int partition = topicPartition.partition();
////            System.out.println(partition);

        }
    }
}
