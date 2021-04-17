package com.wty.maill.service.impl.sendredis;

import com.wty.maill.entity.MailSend;
import com.wty.maill.enumeration.RedisPriorityQueueEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service(value = "pushMaillDeferServiceImpl")
public class PushMaillDeferServiceImpl extends AbstractSendRedis {

    private static final Logger LOGGER = LoggerFactory.getLogger(PushMaillDeferServiceImpl.class);

    private static final RedisPriorityQueueEnum REDIS_PRIORITY = RedisPriorityQueueEnum.DEFER_QUEUE;

    @Override
    public boolean mailPriority(Long sendPriority) {
        return sendPriority > 0 && sendPriority < 4;
    }

    @Override
    public boolean sendRedis(MailSend mailSend) {
        LOGGER.info("PushMaillDeferServiceImpl   ->  {}", REDIS_PRIORITY.getCode());
        return sendRedis(REDIS_PRIORITY,mailSend);
    }
}
