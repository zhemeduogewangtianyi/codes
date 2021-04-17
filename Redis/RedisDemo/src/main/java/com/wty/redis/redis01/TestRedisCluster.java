package com.wty.redis.redis01;

import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;

/**
 * java操作Redis集群
 * */
public class TestRedisCluster {

    private static final String HOST = "192.168.112.131";

    public static void main(String[] args) {

        //1:创建要连接的节点
        Set<HostAndPort> redisClusterNode = new HashSet<>();

        redisClusterNode.add(new HostAndPort(HOST,7001));
        redisClusterNode.add(new HostAndPort(HOST,7002));
        redisClusterNode.add(new HostAndPort(HOST,7003));
        redisClusterNode.add(new HostAndPort(HOST,7004));
        redisClusterNode.add(new HostAndPort(HOST,7005));
        redisClusterNode.add(new HostAndPort(HOST,7006));

        //2：配置Redis的设置
        JedisPoolConfig cfg = new JedisPoolConfig();
        //最大链接
        cfg.setMaxTotal(100);
        //最大空闲
        cfg.setMaxIdle(20);
        //最大等待时间
        cfg.setMaxWaitMillis(-1);
        //在borrow一个jedis实例时，是否提前进行alidate操作
        cfg.setTestOnBorrow(false);

        //3:链接Redis集群，redis节点，超时时间，最大重定向次数，配置
        JedisCluster jc = new JedisCluster(redisClusterNode,6000,100,cfg);

        //4:操作集群
        String name = jc.set("name", "wty");
        System.out.println(name);
        String age = jc.set("age", "19");
        System.out.println(age);

        System.out.println("--------------------------------------");

        String resName = jc.get("name");
        System.out.println(resName);

        String resAge = jc.get("age");
        System.out.println(resAge);

        System.out.println();

        System.out.println(jc.get("name"));
        System.out.println(jc.get("name"));
        System.out.println(jc.get("name"));
        System.out.println(jc.get("name"));
        System.out.println(jc.get("name"));
        System.out.println(jc.get("name"));
        System.out.println(jc.get("age"));

        jc.close();

//        JedisClusterCommand jcc ;
//        jcc.execute(jc);

    }

}
