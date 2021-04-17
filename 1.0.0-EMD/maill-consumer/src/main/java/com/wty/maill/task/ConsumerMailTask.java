package com.wty.maill.task;

import com.wty.maill.enumeration.RedisPriorityQueueEnum;
import com.wty.maill.service.impl.sendredis.ReceiveRedisFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ConsumerMailTask {

    @Autowired
    private ReceiveRedisFactory receiveRedisFactory;

    @Scheduled(initialDelay = 2000,fixedDelay = 2000)
    public void intervalFast(){
        System.out.println("执行 ------- intervalFast");
        receiveRedisFactory.reveice(RedisPriorityQueueEnum.FAST_QUEUE);
    }

    @Scheduled(initialDelay = 15000,fixedDelay = 15000)
    public void intervalNormal(){
        System.out.println("执行 ·····intervalNormal");
        receiveRedisFactory.reveice(RedisPriorityQueueEnum.NORMAL_QUEUE);
    }

    @Scheduled(initialDelay = 30000,fixedDelay = 30000)
    public void intervalDefer(){
        System.out.println("执行 ******* intervalDefer");
        receiveRedisFactory.reveice(RedisPriorityQueueEnum.DEFER_QUEUE);
    }

}
