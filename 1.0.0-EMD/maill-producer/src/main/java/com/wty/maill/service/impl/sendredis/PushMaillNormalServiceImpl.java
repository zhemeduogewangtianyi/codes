package com.wty.maill.service.impl.sendredis;

import com.wty.maill.entity.MailSend;
import com.wty.maill.enumeration.RedisPriorityQueueEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service(value = "pushMaillNormalServiceImpl")
public class PushMaillNormalServiceImpl extends AbstractSendRedis {

    private static Logger LOGGER = LoggerFactory.getLogger(PushMaillNormalServiceImpl.class);

    private static final RedisPriorityQueueEnum REDIS_PRIORITY = RedisPriorityQueueEnum.NORMAL_QUEUE;

    @Override
    public boolean mailPriority(Long sendPriority) {
        return sendPriority > 3 && sendPriority < 7;
    }

    @Override
    public boolean sendRedis(MailSend mailSend) {
        LOGGER.info("PushMaillNormalServiceImpl   ->  {}", REDIS_PRIORITY.getCode());
        return sendRedis(REDIS_PRIORITY,mailSend);
    }
}
