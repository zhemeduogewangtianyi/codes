package com.wty.maill.service.impl.sendredis;

import com.alibaba.fastjson.JSON;
import com.wty.maill.entity.MailSend;
import com.wty.maill.enumeration.RedisPriorityQueueEnum;
import com.wty.maill.service.PushMaillToRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

public abstract class AbstractSendRedis implements PushMaillToRedisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSendRedis.class);

    @Autowired
    private RedisTemplate redisTemplate;

    boolean sendRedis(RedisPriorityQueueEnum queueEnum , MailSend mailSend){
        LOGGER.info("Redis 队列放入消息 放入类型为：{} 入参为： {}",queueEnum.getCode(),JSON.toJSONString(mailSend));
        ListOperations listOperations = redisTemplate.opsForList();
        Long res = listOperations.rightPush(queueEnum.getCode(), JSON.toJSONString(mailSend));
        Long size = listOperations.size(queueEnum.getCode());
        LOGGER.info("Redis 队列放入消息 返回长度为 {}，查询总长度为 {}",res,size);
        return isSendOk(res, size);
    }

    boolean isSendOk(Long res,Long size){
        boolean isOk = res == size;
        LOGGER.info("Redis 队列放入消息 {}",isOk==true ? "成功" : "失败");
        return res == size;
    }

}
