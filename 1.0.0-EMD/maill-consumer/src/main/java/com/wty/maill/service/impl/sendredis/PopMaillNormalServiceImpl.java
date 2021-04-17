package com.wty.maill.service.impl.sendredis;

import com.wty.maill.entity.MailSend;
import com.wty.maill.enumeration.RedisPriorityQueueEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service(value = "popMaillNormalServiceImpl")
public class PopMaillNormalServiceImpl extends AbstractReceiveRedis {

    private static Logger LOGGER = LoggerFactory.getLogger(PopMaillNormalServiceImpl.class);

    private static final RedisPriorityQueueEnum REDIS_PRIORITY = RedisPriorityQueueEnum.NORMAL_QUEUE;

    @Override
    public boolean mailPriority(RedisPriorityQueueEnum queueEnum) {
        if(queueEnum == null){
            return false;
        }
        return queueEnum.getCode().equals(REDIS_PRIORITY.getCode());
    }

    @Override
    public MailSend reveiceRedis() {
        LOGGER.info("PushMaillNormalServiceImpl   ->  {}", REDIS_PRIORITY.getCode());
        return receiveRedis(REDIS_PRIORITY);
    }
}
