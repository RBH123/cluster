package com.ruanbanhai.springboot.demo.controller;


import com.ruanbanhai.springboot.demo.pojo.BannerItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@EnableAutoConfiguration
public class BannerController {

    @RequestMapping("/banner")
    public BannerItem queryBanner(){
        BannerItem bannerItem = new BannerItem();
        bannerItem.setItemImage("https://test-aopsmsg.pingan.com.cn:8020/group2/M00/00/23/HgQUYluGBcOAKsBdAAAOESH23AY327.jpg#w=381;h=486");
        bannerItem.setItemJumpUrl("native://topicpage?topicId=401&topic_id=401");
        return bannerItem;
    }
}
