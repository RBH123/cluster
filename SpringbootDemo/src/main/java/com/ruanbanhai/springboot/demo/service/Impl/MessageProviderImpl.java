package com.ruanbanhai.springboot.demo.service.Impl;

import com.ruanbanhai.springboot.demo.service.MessageProvider;
import com.ruanbanhai.springboot.demo.service.Source;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import javax.annotation.Resource;

@EnableBinding(Source.class)
public class MessageProviderImpl implements MessageProvider {

    @Resource
    private MessageChannel output;

    @Override
    public void push(Object obj) {
        this.output.send(MessageBuilder.withPayload(obj).setHeader("Content-Type", obj.getClass().getSimpleName()).build());
    }
}
