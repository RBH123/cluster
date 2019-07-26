package com.ruanbanhai.springboot.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods implements Serializable {

    @Id
    private long id;
    private String goodsName;
    private String goodsDescription;
}
