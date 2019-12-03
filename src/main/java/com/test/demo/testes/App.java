package com.test.demo.testes;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ElasticSearch代码出错，需要改进
 */
public class App {

    TransportClient client = null;

    @Test
    public void getClient() throws UnknownHostException {
        Settings settings = Settings.builder().put("cluster.name","my-application").build();
        client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"),9300));
        System.out.println("++++++++++++++"+client.toString());
    }

    //创建索引
    @Test
    public void createIndex(){
        //创建索引
        client.admin().indices().prepareCreate("blog").get();
        //关闭资源
        client.close();;
    }
}
