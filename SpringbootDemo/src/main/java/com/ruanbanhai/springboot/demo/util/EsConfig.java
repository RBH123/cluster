package com.ruanbanhai.springboot.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.net.InetAddress;
import java.net.UnknownHostException;


@Slf4j
@Configuration
@PropertySource(value = "classpath:esconfig.properties")
public class EsConfig {

    @Value("${es.hostname}")
    private String hostName;

    @Value("${es.transport}")
    private String port;

    @Value("${es.cluster.name}")
    private String clusterName;

    private final Logger logger = LoggerFactory.getLogger(EsConfig.class);

    @Bean
    public TransportClient getClient() {
        log.info("初始化开始");
        log.info("hostname:{},post:{},clustername:{}", hostName, port, clusterName);
        TransportClient transportClient = null;
        try {
            Settings settings = Settings.builder().put("cluster.name", clusterName).build();
            transportClient = new PreBuiltTransportClient(settings);
            transportClient.addTransportAddress(new TransportAddress(InetAddress.getByName(hostName), Integer.valueOf(port)));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return transportClient;
    }
}
