package com.ruanbanhai.springboot.demo.util.kafka;

import com.ruanbanhai.springboot.demo.pojo.Goods;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;

@Slf4j
public class ConsumerQueue implements Runnable {

    private ArrayBlockingQueue<Goods> queue;

    public ConsumerQueue(ArrayBlockingQueue<Goods> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                consumerBroker();
            }
        } catch (InterruptedException e) {
            log.info("error:{}", e);
        }
    }

    public void consumerBroker() throws InterruptedException {
        Goods goods = queue.take();
        log.info("阻塞队列获取数据:{}", goods);
    }
}
