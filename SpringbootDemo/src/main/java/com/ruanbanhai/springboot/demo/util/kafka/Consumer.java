package com.ruanbanhai.springboot.demo.util.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruanbanhai.springboot.demo.pojo.Goods;
import com.ruanbanhai.springboot.demo.util.ObjectToMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;
import java.util.PrimitiveIterator;
import java.util.PriorityQueue;
import java.util.concurrent.*;

@Slf4j
@Component
public class Consumer {

    @Autowired
    private MongoTemplate mongoTemplate;

    //array阻塞队列,有界
    private ArrayBlockingQueue<Goods> queue = new ArrayBlockingQueue<Goods>(10);

    @KafkaListener(topics = {"msg"},idIsGroup = false,id = "consumer1")
    public void receive(ConsumerRecord<?, ?> consumerRecord) throws Exception {
        System.out.println("消费者1消费");
        Optional<Object> value = Optional.ofNullable(consumerRecord.value());
        if (value.isPresent()) {
            String jsonString = consumerRecord.value().toString();
            Goods goods = JSONObject.parseObject(jsonString, Goods.class);
            queue.put(goods);
            ConsumerQueue consumerQueue = new ConsumerQueue(queue);
            ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10, 15, 2, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));
            poolExecutor.execute(consumerQueue);
        }
    }

//    @PostConstruct
//    public void init() {
//        ConsumerQueue consumerQueue = new ConsumerQueue(queue);
//        consumerQueue.run();
//    }

    @KafkaListener(topics = {"msg"},idIsGroup = false,id = "consumer2")
    public void receive2(ConsumerRecord<?, ?> consumerRecord) throws Exception {
        System.out.println("消费者2消费");
        Optional<Object> value = Optional.ofNullable(consumerRecord.value());
        if (value.isPresent()) {
            String jsonString = consumerRecord.value().toString();
            Goods goods = JSONObject.parseObject(jsonString, Goods.class);
            queue.put(goods);
            ConsumerQueue consumerQueue = new ConsumerQueue(queue);
            ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10, 15, 2, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));
            poolExecutor.execute(consumerQueue);
        }
    }
}
