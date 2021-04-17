package com.wty.maill.task;

import com.wty.maill.entity.MailSend;
import com.wty.maill.enumeration.MailStatusEnum;
import com.wty.maill.service.MailSendService;
import com.wty.maill.service.impl.sendredis.SendRedisFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
public class ReTryTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReTryTask.class);

    @Autowired
    private MailSendService mailSendService;

    @Autowired
    private SendRedisFactory sendRedisFactory;

    @Scheduled(initialDelay = 2000,fixedDelay = 15000)
    public void reTry() throws Exception {
        LOGGER.info("定时执行 ------- retry");
        List<MailSend> mailSends = mailSendService.queryMainSendByStatus();
        for(MailSend mailSend : mailSends){
            boolean send = sendRedisFactory.send(mailSend);
            mailSend.setSendStatus(MailStatusEnum.SEND_IN.getCode());
            LOGGER.info("重新发送 {} -> {}",mailSend.getSendId(),send ? "成功" : "失败");
        }


    }

}
