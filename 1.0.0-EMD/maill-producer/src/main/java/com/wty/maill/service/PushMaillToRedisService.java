package com.wty.maill.service;

import com.wty.maill.entity.MailSend;

public interface PushMaillToRedisService {

    boolean mailPriority(Long sendPriority);

    boolean sendRedis(MailSend mailSend);

}
