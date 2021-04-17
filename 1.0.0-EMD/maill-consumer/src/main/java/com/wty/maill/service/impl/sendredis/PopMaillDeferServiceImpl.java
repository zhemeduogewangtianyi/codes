package com.wty.maill.service.impl.sendredis;

import com.wty.maill.entity.MailSend;
import com.wty.maill.enumeration.RedisPriorityQueueEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service(value = "popMaillDeferServiceImpl")
public class PopMaillDeferServiceImpl extends AbstractReceiveRedis {

    private static final Logger LOGGER = LoggerFactory.getLogger(PopMaillDeferServiceImpl.class);

    private static final RedisPriorityQueueEnum REDIS_PRIORITY = RedisPriorityQueueEnum.DEFER_QUEUE;

    @Override
    public boolean mailPriority(RedisPriorityQueueEnum queueEnum) {
        if(queueEnum == null){
            return false;
        }
        return queueEnum.getCode().equals(REDIS_PRIORITY.getCode());
    }

    @Override
    public MailSend reveiceRedis() {
        LOGGER.info("PushMaillDeferServiceImpl   ->  {}", REDIS_PRIORITY.getCode());
        return receiveRedis(REDIS_PRIORITY);
    }
}
