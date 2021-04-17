package com.wty.maill.service.impl.sendredis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wty.maill.entity.MailSend;
import com.wty.maill.enumeration.RedisPriorityQueueEnum;
import com.wty.maill.service.PushMaillToRedisService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

public abstract class AbstractReceiveRedis implements PushMaillToRedisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractReceiveRedis.class);

    @Autowired
    private RedisTemplate redisTemplate;

    MailSend receiveRedis(RedisPriorityQueueEnum queueEnum){
        LOGGER.info("Redis 队列取出类型为： {} ",queueEnum.getCode());
        ListOperations<String,String> listOperations = redisTemplate.opsForList();
        String msg = listOperations.leftPop(queueEnum.getCode());
        MailSend res = null;
        if(StringUtils.isNoneBlank(msg)){
            res = JSONObject.parseObject(msg, MailSend.class);
        }
        LOGGER.info("Redis 队列取出消息内容： {}",JSON.toJSONString(res));
        return res;
    }

}
