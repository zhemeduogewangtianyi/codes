package com.wty.maill.service.impl.sendredis;

import com.wty.maill.entity.MailSend;
import com.wty.maill.enumeration.RedisPriorityQueueEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service(value = "pushMaillFastServiceImpl")
public class PushMaillFastServiceImpl extends AbstractSendRedis {

    private static final Logger LOGGER = LoggerFactory.getLogger(PushMaillFastServiceImpl.class);

    private static final RedisPriorityQueueEnum REDIS_PRIORITY = RedisPriorityQueueEnum.FAST_QUEUE;

    @Override
    public boolean mailPriority(Long sendPriority) {
        return sendPriority > 6 && sendPriority < 10;
    }

    @Override
    public boolean sendRedis(MailSend mailSend) {
        LOGGER.info("PushMaillFastServiceImpl   ->  {}", REDIS_PRIORITY.getCode());
        return sendRedis(REDIS_PRIORITY,mailSend);
    }
}
