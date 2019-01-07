package com.ruanbanhai.springboot.demo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String hostname;
    @Value("${spring.redis.port}")
    private String port;
    @Value("${spring.redis.pool.max-idle}")
    private String maxIdle;
    @Value("${spring.redis.pool.max-wait}")
    private String maxWait;
    @Value("${spring.redis.pool.max-active}")
    private String maxActive;
    @Value("${spring.redis.cluster.nodes}")
    private String nodes;
    @Value("${spring.redis.timeout}")
    private String timeout;

    @Bean(name = "jedisCluster")
    public JedisCluster jedisCluster(){
        String[] split = nodes.split(",");
        HashSet<HostAndPort> cluster = new HashSet<>();
        for (String s : split) {
            String[] node = s.split(":");
            cluster.add(new HostAndPort(node[0],Integer.parseInt(node[1])));
        }
        return new JedisCluster(cluster,Integer.parseInt(timeout));

    }

    @Bean(name = "redisTemplate")
    public RedisTemplate reidsTemplate(){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory(
                hostname,
                Integer.parseInt(port),
                Integer.parseInt(maxIdle),
                Integer.parseInt(maxActive),
                Integer.parseInt(maxWait)));
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean(name = "stringRedisTemplate")
    public StringRedisTemplate stringReidsTemplate(
            @Value("${redis.host}")String hostname,
            @Value("${redis.port}")String port,
            @Value("${redis.pool.max-idle}")String maxIdle,
            @Value("${redis.pool.max-wait}")String maxWait,
            @Value("${redis.pool.max-active}")String maxActive){
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(stringRedisConnectionFactory(
                hostname,
                Integer.parseInt(port),
                Integer.parseInt(maxIdle),
                Integer.parseInt(maxActive),
                Integer.parseInt(maxWait)));
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    //构造链接工厂
    private RedisConnectionFactory redisConnectionFactory(String hostname, int port, int maxIdle, int maxActive, int maxWait){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(hostname);
        jedisConnectionFactory.setDatabase(0);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig(maxIdle,maxActive,maxWait));
        jedisConnectionFactory.afterPropertiesSet();
        return jedisConnectionFactory;
    }

    private RedisConnectionFactory stringRedisConnectionFactory(String hostname, int port, int maxIdle, int maxActive, int maxWait){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(hostname);
        jedisConnectionFactory.setDatabase(0);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig(maxIdle,maxActive,maxWait));
        jedisConnectionFactory.afterPropertiesSet();
        return jedisConnectionFactory;
    }

    //构造连接池
    private JedisPoolConfig jedisPoolConfig(int maxIdle, int maxActive, int maxWait){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setMaxIdle(maxIdle);
        return jedisPoolConfig;
    }
}
