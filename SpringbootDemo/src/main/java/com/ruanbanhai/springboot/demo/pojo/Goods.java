package com.ruanbanhai.springboot.demo.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Goods implements Serializable{
    private long id;
    private String goodsName;
    private String goodsDescription;
}
