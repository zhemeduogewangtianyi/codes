package com.wty.maill.service.impl.sendredis;

import com.wty.maill.entity.MailSend;
import com.wty.maill.entity.vo.MailDataVO;
import com.wty.maill.enumeration.MailStatusEnum;
import com.wty.maill.enumeration.RedisPriorityQueueEnum;
import com.wty.maill.service.MailSendService;
import com.wty.maill.service.PushMaillToRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.List;

@Component
public class ReceiveRedisFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiveRedisFactory.class);

    @Autowired
    private List<PushMaillToRedisService> pushMaillToRedisServices;

    @Autowired
    private MailSendService mailSendService;

    /** 消费信息 */
    public MailSend reveice(RedisPriorityQueueEnum queueEnum){
        MailSend mailSend = null;
        Exception ex = null;
        try{
            for(PushMaillToRedisService service : pushMaillToRedisServices){
                if(service.mailPriority(queueEnum)){
                    mailSend = service.reveiceRedis();
                    if(mailSend == null){
                        LOGGER.info("队列中暂无消息");
                        return null;
                    }
                    MailDataVO mailDataVO = mailSendService.processMessage(mailSend);
                    mailSendService.sendMessage(mailDataVO);
                }
            }
        }catch(MessagingException e){
            ex = e;
            LOGGER.error("{}  消息发送出现异常， 异常信息是： {}",mailSend.getSendId(),e);
        }catch (Exception e){
            ex = e;
            LOGGER.error("{}  系统出现异常， 异常信息是： {}",mailSend.getSendId(),e);
        }
        updateMailStatus(mailSend,ex);
        return mailSend;
    }

    /** 乐观更新邮件发送状态 */
    public void updateMailStatus(MailSend mailSend,Exception e){
        if(e == null){
            LOGGER.info("邮件发送成功，准备乐观更新状态、版本 -> ID： {}",mailSend.getSendId());
            mailSend.setSendStatus(MailStatusEnum.SEN_OD.getCode());
        }else{
            LOGGER.info("邮件发送失败 -> {} -> 确认重新拉取信息",mailSend.getSendId());
            if(mailSend.getSendCount() < 4){
                mailSend.setSendStatus(MailStatusEnum.DRAFT.getCode());
                LOGGER.info("邮件发送失败，重新拉取信息，准备乐观更新状态、版本 -> ID： {} ， 次数： {}",mailSend.getSendId(),mailSend.getSendCount());
            }else{
                mailSend.setSendStatus(MailStatusEnum.SEND_ERR.getCode());
                LOGGER.info("邮件发送失败，重新拉取信息，准备乐观更新状态、版本 -> ID： {} ， 次数： {}",mailSend.getSendId(),mailSend.getSendCount());
            }
        }
        int i = mailSendService.updateMailStatus(mailSend);
    }

}
