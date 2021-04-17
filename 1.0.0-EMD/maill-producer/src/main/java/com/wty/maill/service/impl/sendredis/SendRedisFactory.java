package com.wty.maill.service.impl.sendredis;

import com.wty.maill.entity.MailSend;
import com.wty.maill.service.PushMaillToRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SendRedisFactory {

    @Autowired
    private List<PushMaillToRedisService> pushMaillToRedisServices;

    public boolean send(MailSend mailSend){
        for(PushMaillToRedisService service : pushMaillToRedisServices){
            if(service.mailPriority(mailSend.getSendPriority())){
                return service.sendRedis(mailSend);
            }
        }
        return false;
    }

}
