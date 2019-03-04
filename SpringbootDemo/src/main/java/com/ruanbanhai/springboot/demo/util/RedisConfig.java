package com.ruanbanhai.springboot.demo.util;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

@Configuration(value = "spring.redis")
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String hostname;
    @Value("${spring.redis.port}")
    private String port;
    @Value("${spring.redis.lettuce.pool.max-idle}")
    private String maxIdle;
    @Value("${spring.redis.lettuce.pool.max-wait}")
    private String maxWait;
    @Value("${spring.redis.lettuce.pool.max-active}")
    private String maxActive;
    @Value("${spring.redis.cluster.nodes}")
    private String nodes;

    @Bean(name = "jedisCluster")
    public JedisCluster jedisCluster(){
        String[] split = nodes.split(",");
        Set<HostAndPort> cluster = new HashSet<HostAndPort>();
        for (String s : split) {
            String[] node = s.split(":");
            cluster.add(new HostAndPort(node[0],Integer.parseInt(node[1])));
        }
        return new JedisCluster(cluster);

    }

    @Bean(name = "redisQueue")
    public RedisTemplate reidsTemplate(){
        System.out.println(hostname);
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(getLettuceConnectionFactory0());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }

    @Bean(name = "stringRedisTemplate")
    public StringRedisTemplate stringReidsTemplate(){
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(getLettuceConnectionFactory1());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

//    //构造链接工厂
//    private RedisConnectionFactory redisConnectionFactory(String hostname, int port, int maxIdle, int maxActive, int maxWait){
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
//        jedisConnectionFactory.setHostName(hostname);
//        jedisConnectionFactory.setDatabase(0);
//        jedisConnectionFactory.setPort(port);
//        jedisConnectionFactory.setPoolConfig(jedisPoolConfig(maxIdle,maxActive,maxWait));
//        jedisConnectionFactory.afterPropertiesSet();
//        return jedisConnectionFactory;
//    }
    public LettuceConnectionFactory getLettuceConnectionFactory0(){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(0);
        redisStandaloneConfiguration.setHostName(hostname);
        redisStandaloneConfiguration.setPort(Integer.parseInt(port));
        LettuceClientConfiguration lettuceClientConfiguration = LettucePoolingClientConfiguration
                .builder().poolConfig(poolConfig()) 
                .build();
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration);
        return connectionFactory;
    }

    public LettuceConnectionFactory getLettuceConnectionFactory1(){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(1);
        redisStandaloneConfiguration.setHostName(hostname);
        redisStandaloneConfiguration.setPort(Integer.parseInt(port));
        LettuceClientConfiguration lettuceClientConfiguration = LettucePoolingClientConfiguration.builder()
                .poolConfig(poolConfig())
                .build();
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration);
        return connectionFactory;
    }

//    private RedisConnectionFactory stringRedisConnectionFactory(String hostname, int port, int maxIdle, int maxActive, int maxWait){
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
//        jedisConnectionFactory.setHostNamse(hostname);
//        jedisConnectionFactory.setDatabase(0);
//        jedisConnectionFactory.setPort(port);
//        jedisConnectionFactory.setPoolConfig(jedisPoolConfig(maxIdle,maxActive,maxWait));
//        jedisConnectionFactory.afterPropertiesSet();
//        return jedisConnectionFactory;
//    }

    //构造连接池
//    private JedisPoolConfig jedisPoolConfig(int maxIdle, int maxActive, int maxWait){
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxWaitMillis(maxWait);
//        jedisPoolConfig.setMaxTotal(maxActive);
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        return jedisPoolConfig;
//    }
    public GenericObjectPoolConfig poolConfig(){
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxTotal(Integer.parseInt(maxActive));
        genericObjectPoolConfig.setMaxIdle(Integer.parseInt(maxIdle));
        return genericObjectPoolConfig;
    }
}
