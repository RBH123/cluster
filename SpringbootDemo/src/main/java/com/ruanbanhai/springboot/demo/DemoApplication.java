package com.ruanbanhai.springboot.demo;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.ruanbanhai.springboot.demo.util.kafka.KafkaProducer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import javax.annotation.PostConstruct;

@SpringBootApplication
@ServletComponentScan
@EnableCaching
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "com.ruanbanhai.springboot.demo")
@ImportResource(value = "classpath:*.xml")
@MapperScan(value = "com.ruanbanhai.springboot.demo.dao")
public class DemoApplication {

    @Autowired
    public KafkaProducer producer;

    @PostConstruct
    public void init() {
        producer.send();
    }

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
        c.setIgnoreUnresolvablePlaceholders(true);
        return c;
    }
}

