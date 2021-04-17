package com.wty.maill.service;

import com.wty.maill.entity.MailSend;
import com.wty.maill.enumeration.RedisPriorityQueueEnum;

public interface PushMaillToRedisService {

    boolean mailPriority(RedisPriorityQueueEnum queueEnum);

    MailSend reveiceRedis();

}
