package com.wty.maill.controller;

import com.alibaba.fastjson.JSON;
import com.wty.maill.constant.Const;
import com.wty.maill.entity.MailSend;
import com.wty.maill.enumeration.MailStatusEnum;
import com.wty.maill.service.MailSendService;
import com.wty.maill.service.impl.sendredis.SendRedisFactory;
import com.wty.maill.utils.KeyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/producer")
public class ProducerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerController.class);

    @Autowired
    private MailSendService mailSendService;

    @Autowired
    private SendRedisFactory sendRedisFactory;

    @Transactional
    @RequestMapping(value = "/send",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public void send(@RequestBody(required = false) MailSend mailSend){
        LOGGER.info("发送邮件Controller -> 入参 {}", JSON.toJSONString(mailSend));
        try{
            //1：数据校验，业务校验

            //2：insert
            mailSend = mailSend == null ? new MailSend() : mailSend;
            mailSend.setSendId(KeyUtils.generatorUUID());
            mailSend.setSendCount(0L);
            mailSend.setSendStatus(MailStatusEnum.DRAFT.getCode());
            mailSend.setVersion(0L);
            mailSend.setUpdateBy(Const.SYS_RUNTIME);
            mailSend.setSendMail("邮箱外发服务关闭通知");
            mailSend.setRemark("这是一个测试用的数据");
//            mailSend.setSendTo("279728948@qq.com");
//            mailSend.setSendTo("1965664138@qq.com");
//            mailSend.setSendTo("1119584188@qq.com");
//            mailSend.setSendTo("229719031@qq.com");
//            mailSend.setSendTo("348530146@qq.com");
            mailSend.setSendTo("1023386850@qq.com");
//            mailSend.setSendTo("ay2853@126.com");
            StringBuffer content = new StringBuffer("尊敬的用户：");
            content.append("\n");
            content.append("\n");
            mailSend.setSendContent(content.toString());
            mailSend.setSendPriority(9L);
            mailSendService.insert(mailSend);

            //3:把数据扔到redis里
            boolean send = sendRedisFactory.send(mailSend);

            //count+1 -> 投递次数 + 1
            mailSend.setSendCount(mailSend.getSendCount() + 1);

            //4：扔失败了
            if (!send){
                LOGGER.info("消息投入到队列失败，等待轮询重新投递 -> {} ， 当前投递次数为 -> {}", mailSend.getSendId(),mailSend.getSendCount());
                mailSendService.updateByCondition(mailSend);
            }
            //成功了，修改状态
            else{
                mailSend.setSendStatus(MailStatusEnum.SEND_IN.getCode());
                mailSendService.updateByCondition(mailSend);
            }

        }catch(Exception e){
            LOGGER.error("发送邮件Controller失败！ {}", e);
        }

    }

}
