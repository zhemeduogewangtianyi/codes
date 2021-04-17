package com.es.demo;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class RestHighLevelClientFactory {

    private static RestHighLevelClient restHighLevelClient;

    private RestHighLevelClientFactory(){

    }

    public static RestHighLevelClient getRestHighLevelClient(){
        if(restHighLevelClient == null){
            synchronized (RestHighLevelClientFactory.class){
                if(restHighLevelClient == null){
                    restHighLevelClient = new RestHighLevelClient(
                            RestClient.builder(
                                    new HttpHost("192.168.112.152", 9200, "http"),
                                    new HttpHost("192.168.112.152", 9200, "http")));
                }
            }
        }
        return restHighLevelClient;
    }

    public static void close(){
        try {
            restHighLevelClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
