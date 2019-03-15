package com.ruanbanhai.springboot.demo.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
public class Goods{
    @Id
    private long id;
    private String goodsName;
    private String goodsDescription;
}
