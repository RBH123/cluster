package com.ruanbanhai.springboot.demo.controller


import com.ruanbanhai.springboot.demo.pojo.BannerItem
import lombok.extern.slf4j.Slf4j
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
@EnableAutoConfiguration
class BannerController {

    @RequestMapping("/banner")
    fun queryBanner(): BannerItem {
        val bannerItem = BannerItem()
        bannerItem.itemImage = "https://test-aopsmsg.pingan.com.cn:8020/group2/M00/00/23/HgQUYluGBcOAKsBdAAAOESH23AY327.jpg#w=381;h=486"
        bannerItem.itemJumpUrl = "native://topicpage?topicId=401&topic_id=401"
        return bannerItem
    }
}
