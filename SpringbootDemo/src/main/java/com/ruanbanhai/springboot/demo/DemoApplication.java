package com.ruanbanhai.springboot.demo;

import com.ruanbanhai.springboot.demo.util.RedisConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@SpringBootApplication
@ServletComponentScan
@EnableAutoConfiguration
@EnableCaching
@ImportResource(value = "classpath:*.xml")
@MapperScan(value = "com.ruanbanhai.springboot.demo.dao")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		RedisConfig redisConfig = new RedisConfig();
	}

	@Bean
 	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
 		c.setIgnoreUnresolvablePlaceholders(true);
 		return c;
}
}

