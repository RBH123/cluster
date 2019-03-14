package com.ruanbanhai.springboot.demo.service;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface Source {

    String OUTPUT="output";

    @Output(value = Source.OUTPUT)
    MessageChannel output();

}
