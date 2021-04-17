package com.es.demo;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.sniff.SniffOnFailureListener;
import org.elasticsearch.client.sniff.Sniffer;

public class RestClientFactory {

    private static RestClient restClient;

    private RestClientFactory(){

    }

    public static RestClient getRestClient(){
        if(restClient == null){

            synchronized(RestClientFactory.class){

                if(restClient == null){

                    restClient = RestClient.builder(
                            new HttpHost("192.168.112.152", 9200, "http"),
                            new HttpHost("192.168.112.153", 9200, "http"))
                            .build();

                }

            }

        }

        return restClient;
    }

}
