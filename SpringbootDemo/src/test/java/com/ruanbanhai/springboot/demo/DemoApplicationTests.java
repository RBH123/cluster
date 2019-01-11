package com.ruanbanhai.springboot.demo;

import com.ruanbanhai.springboot.demo.pojo.BannerItem;
import com.ruanbanhai.springboot.demo.pojo.Goods;
import com.ruanbanhai.springboot.demo.util.Consumer;
import com.ruanbanhai.springboot.demo.util.Producer;
import com.ruanbanhai.springboot.demo.util.WebCrawler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
    }


    @Test
    public void test03() {
        BannerItem bannerItem = new BannerItem();
        bannerItem.setItemImage("https://test-aopsmsg.pingan.com.cn:8020/group2/M00/00/23/HgQUYluGBcOAKsBdAAAOESH23AY327.jpg#w=381;h=486");
        bannerItem.setItemJumpUrl("native://topicpage?topicId=401&topic_id=401");
        System.out.println(bannerItem);

    }


    @Autowired
    private TransportClient client;

    /**
     * 构建索引映射
     */
    @Test
    public void test04() {
        try {
            CreateIndexRequestBuilder index = client.admin().indices().prepareCreate("user7");
            XContentBuilder xContentBuilder = jsonBuilder()
                    .startObject()
                    .startObject("properties")
                    .startObject("id").field("type", "integer").endObject()
                    .startObject("username").field("type", "text").field("index", "true").field("analyzer", "ik").endObject()
                    .startObject("age").field("type", "integer").endObject()
                    .startObject("sex").field("type", "text").endObject()
                    .startObject("password").field("type", "text").endObject()
                    .endObject()
                    .endObject();
            index.addMapping("type", xContentBuilder);
            CreateIndexResponse createIndexResponse = index.execute().actionGet();
        } catch (Exception e) {
            log.error("error", e);
        }
    }

    /**
     * 插入数据
     */
    @Test
    public void test05() {
        Map<String, Object> map = new HashMap<>();
        map.put("username", "张三今天是个好日子");
        map.put("age", "20");
        map.put("sex", "man");
        map.put("password", "123456");
        IndexRequestBuilder indexRequestBuilder = client.prepareIndex("user6", "type").setSource(map);
        indexRequestBuilder.execute().actionGet();
    }

    /**
     * 批量创建索引插入数据
     */
    @Test
    public void test06() {
        BulkProcessor bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long l, BulkRequest bulkRequest) {

            }

            @Override
            public void afterBulk(long l, BulkRequest bulkRequest, BulkResponse bulkResponse) {

            }

            @Override
            public void afterBulk(long l, BulkRequest bulkRequest, Throwable throwable) {
                System.out.println("error:" + throwable.getMessage() + ",cause:" + throwable.getCause());
            }
        }).setBulkActions(5)
                .setBulkSize(new ByteSizeValue(10, ByteSizeUnit.MB))
                .setConcurrentRequests(1)
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                .build();

        //批量插入操作
//        for (int i = 30; i < 40; i++) {
//            Map<String,Object> map = new HashMap<>();
//            map.put("username","zhangsan"+i);
//            map.put("age",20+i);
//            bulkProcessor.add(new IndexRequest(7+i+"","type").source(map));
//        }
//        bulkProcessor.close();

        //批量删除操作
//        for (int i = 0; i < 10; i++) {
//            bulkProcessor.add(new DeleteRequest(7+i+""));
//        }
//        bulkProcessor.close();
        AcknowledgedResponse acknowledgedResponse = client.admin().indices().prepareClose("7").execute().actionGet();
        System.out.println(acknowledgedResponse);
    }

    /**
     * 查询数据
     */
    @Test
    public void test07() {
        Map<String, Object> map = new HashMap<>();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("username", "张三今天是个好日子");
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch("user6").setQuery(termQueryBuilder);
        SearchResponse searchResponse = searchRequestBuilder.get();
        SearchHits hits = searchResponse.getHits();
        long totalHits = hits.getTotalHits();
        System.out.println(totalHits);
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit documentFields : hits1) {
            String index = documentFields.getIndex();
            map = documentFields.getSourceAsMap();
            System.out.println(map);
        }
    }

    @Test
    public void test08() throws IOException {
        String url = "https://uland.taobao.com/sem/tbsearch?refpid=mm_26632258_3504122_32538762&clk1=0a78bf1cd08040a7fcce8bd679c53b4e&keyword=%E8%BF%9E%E8%A1%A3%E8%A3%99&page=0";
        URL urls = new URL(url);
        URLConnection urlConnection = urls.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("F:\\Code\\SpringbootDemo\\src\\main\\resources\\demo.txt"))));
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            bufferedWriter.write(line, 0, line.length());
        }
        bufferedWriter.close();
        bufferedReader.close();
    }

    @Test
    public void test09() throws IOException {
        String url = "http://image.baidu.com/search/index?tn=baiduimage&ct=201326592&lm=-1&cl=2&ie=gb18030&word=%B0%D9%B6%C8%20%CD%BC%C6%AC&fr=ala&ala=1&alatpl=adress&pos=0&hs=2&xthttps=000000";
        WebCrawler.crawlerUrl(url);
    }

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    @Test
    public void test12() {
//        Goods goods = new Goods();
//        goods.setGoodsDescription("今天天气好晴朗");
//        goods.setGoodsName("天气");
//        goods.setId(1);
        redisTemplate.opsForValue().set("tianqi1", "goods");
    }

    @Test
    public void test13() {
        Object tianqi = redisTemplate.opsForValue().get("tianqi1");
        System.out.println(tianqi);
    }

    @Test
    public void testJedisCluster() {
        HashSet<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.17.14", 9001));
        nodes.add(new HostAndPort("192.168.17.14", 9002));
        nodes.add(new HostAndPort("192.168.17.14", 9003));
        nodes.add(new HostAndPort("192.168.17.15", 9001));
        nodes.add(new HostAndPort("192.168.17.15", 9002));
        nodes.add(new HostAndPort("192.168.17.15", 9003));
        nodes.add(new HostAndPort("192.168.17.16", 9001));
        nodes.add(new HostAndPort("192.168.17.16", 9002));
        nodes.add(new HostAndPort("192.168.17.16", 9003));
        JedisCluster jc = new JedisCluster(nodes);
        jc.append("username1", "zhangsan");
        String username = jc.get("username");
        System.out.println(username);
    }

    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;

    @Test
    public void test14() {
        System.out.println(jedisCluster);
        jedisCluster.set("tianqin", "今天天气好晴朗");
        jedisCluster.expire("tianqi", 60);
    }

    @Test
    public void test15() throws Exception {
        Producer producer = new Producer("producerName");
        for (int i = 0; i < 10; i++) {
            producer.putMessage("tianqi" + i, "今天天气好晴朗" + i);
        }
    }

    @Test
    public void test16() {
        Consumer consumer = new Consumer("consumerName");
        for (int i = 0; i < 10; i++) {
            String s = consumer.receiveMessage("tianqi" + i);
            System.out.println(s);
        }
    }

    public void test17(Object object) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Map<String,Object> map = new HashMap<>();
        BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String name = propertyDescriptor.getName();
            if (!name.equals("class")) {
                Method readMethod = propertyDescriptor.getReadMethod();
                Object value = readMethod.invoke(object);
                map.put(name,value);
            }
        }
        BulkProcessor bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {

            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {

            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                System.out.println("message:" + failure.getMessage() + ",cause:" + failure.getCause());
            }
        })
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                .setBulkActions(5)
                .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))
                .setConcurrentRequests(2)
                .build();
        bulkProcessor.add(new IndexRequest("user7","type").source(map));
        bulkProcessor.close();
    }

    @Test
    public void test18() throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        Goods goods = new Goods();
        goods.setId(1);
        goods.setGoodsName("中国人");
        goods.setGoodsDescription("国家富强");
        test17(goods);
    }

    /*
    全部查询
     */
    @Test
    public void test19(){
        SearchResponse searchResponse = client.prepareSearch("user7").setTypes("type").setQuery(QueryBuilders.matchAllQuery()).get();
        SearchHits hits = searchResponse.getHits();
        Iterator<SearchHit> iterator = hits.iterator();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }
    }

    /*
    通配符查询
     */
    @Test
    public void test20(){
        SearchResponse searchResponse = client.prepareSearch("user7").setTypes("type").setQuery(QueryBuilders.wildcardQuery("goodsDescription", "*中*")).get();
        SearchHits hits = searchResponse.getHits();
        Iterator<SearchHit> iterator = hits.iterator();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }
    }

    /*
    全文分词查询
     */
    @Test
    public void test21(){
        SearchResponse searchResponse = client.prepareSearch("user7").setTypes("type").setQuery(QueryBuilders.queryStringQuery("中国")).get();
        SearchHits hits = searchResponse.getHits();
        Iterator<SearchHit> iterator = hits.iterator();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }
    }

    /**
     * termQuery 会根据提供的参数全文匹配
     * matchQuery  会将参数进行拆分，然后再进行全文匹配
     */
    @Test
    public void test22(){
        SearchResponse searchResponse = client.prepareSearch("user7").setTypes("type").setQuery(QueryBuilders.termQuery("goodsDescription", "共和国")).get();
        SearchHits hits = searchResponse.getHits();
        if(hits != null && hits.getTotalHits() != 0){
            for (SearchHit hit : hits) {
                System.out.println("term查询");
                String sourceAsString = hit.getSourceAsString();
            }
        }else {
            SearchResponse searchResponse1 = client.prepareSearch("user7").setTypes("type").setQuery(QueryBuilders.matchQuery("goodsDescription", "共和国")).get();
            SearchHits hits1 = searchResponse1.getHits();
            if(hits1 != null && hits1.getTotalHits() != 0){
                for (SearchHit documentFields : hits1) {
                    System.out.println("match查询");
                    String sourceAsString = documentFields.getSourceAsString();
                    System.out.println(sourceAsString);
                }
            }
        }
    }


    /**
     * 批量删除
     */
    @Test
    public void test23(){
        BulkProcessor bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {

            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {

            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                System.out.println("exception:" + failure.getMessage() + ",cause:" + failure.getCause());
            }
        })
                .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))
                .setConcurrentRequests(1)
                .setFlushInterval(TimeValue.timeValueMillis(5))
                .setBulkActions(5)
                .build();
        for (int i = 1; i < 10; i++) {
            bulkProcessor.add(new DeleteRequest(i+7+""));
        }
        bulkProcessor.close();
    }

    @Test
    public void test24(){
        String[] texts = {"国","中国"};
        SearchResponse searchRequestBuilder = client.prepareSearch("user7").setTypes("type").setQuery(QueryBuilders.moreLikeThisQuery(texts)).get();
        SearchHits hits = searchRequestBuilder.getHits();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }
    }
}

