package com.wty.redis.redis01;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class TestSingleRedis {

    /**
     * Jedis 单独的实例化对象
     * 单独去连接一台机器
     * */
    private static Jedis jedis;

    /**
     * Jedis分片，提高系统的性能
     * 主从/哨兵 模式使用
     * 单机也能用
     * 逻辑上的表述：分片
     * */
    private static ShardedJedis shardedJedis;

    /** Jedis连接池 */
    private static ShardedJedisPool shardedJedisPool;



}
