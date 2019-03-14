package com.ruanbanhai.springboot.demo.service;

import com.ruanbanhai.springboot.demo.pojo.User;

public interface MessageProvider {

    public void push(User user);
}
