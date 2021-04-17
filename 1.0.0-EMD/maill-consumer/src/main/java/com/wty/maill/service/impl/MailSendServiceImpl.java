package com.wty.maill.service.impl;

import com.alibaba.fastjson.JSON;
import com.wty.maill.annotation.DataSourceCondition;
import com.wty.maill.entity.MailSend;
import com.wty.maill.entity.vo.MailDataVO;
import com.wty.maill.enumeration.MailStatusEnum;
import com.wty.maill.enumeration.RedisPriorityQueueEnum;
import com.wty.maill.mapper.MailSend1Mapper;
import com.wty.maill.mapper.MailSend2Mapper;
import com.wty.maill.service.MailSendService;
import com.wty.maill.service.impl.sendredis.ReceiveRedisFactory;
import com.wty.maill.utils.GeneratorMailTemplateHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailSendServiceImpl implements MailSendService {

    /** 日志 */
    private static final Logger LOGGER = LoggerFactory.getLogger(MailSendServiceImpl.class);

    @Resource
    private MailSend1Mapper mailSend1Mapper;

    @Resource
    private MailSend2Mapper mailSend2Mapper;

    @Autowired
    private GeneratorMailTemplateHelper templateHelper;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    @DataSourceCondition
    public int updateMailStatus(MailSend mailSend){
        LOGGER.info("乐观更新，通过version 和 ID -> 更新数据： {}", JSON.toJSONString(mailSend));
        int hashCode = mailSend.getSendId().hashCode();
        int res;
        //乐观更新，通过version 和 ID
        if(conditionMapper(hashCode)){
            LOGGER.info("更新 HashCode {} , mapperSend - 1",hashCode);
            res = mailSend1Mapper.updateByPrimaryKeyAndVersion(mailSend);
        }else{
            LOGGER.info("更新 HashCode {} , mapperSend - 2",hashCode);
            res = mailSend2Mapper.updateByPrimaryKeyAndVersion(mailSend);
        }
        LOGGER.info("乐观更新 完毕 {} , 受影响行数是 -> {}  更新后数据： {}",res);
        return res;
    }


    /** 分库算法 */
    public boolean conditionMapper(int hashCode){
        if(hashCode % 2 == 0){
            return true;
        }
        return false;
    }


    @Override
    public MailDataVO processMessage(MailSend mailSend) {

        //1：准备数据
        Map<String,Object> params = new HashMap<>();
        params.put("messageId",mailSend.getSendTo());
        params.put("subject",mailSend.getSendMail());
        params.put("content",mailSend.getSendContent());


        MailDataVO vo = new MailDataVO();
        vo.setParams(params);
        vo.setUserId(mailSend.getSendId());
        vo.setTemplateName("SHEET");
        vo.setFrom("ant_process@aliyun.com");
        vo.setTo(mailSend.getSendTo());
        vo.setSubject(mailSend.getSendMail());

        //2:模板渲染
        MailDataVO mailTemplate = templateHelper.generatorTemplate(vo);
        return mailTemplate;
    }

    @Override
    public void sendMessage(MailDataVO data) throws MessagingException {
        MimeMessage mime = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mime,true,"UTF-8");
        mime.addHeader("X-Priority", "3");
        mime.addHeader("X-MSMail-Priority", "Normal");
        mime.addHeader("X-Mailer","Microsoft Outlook Express 6.00.2900.2869");
        mime.addHeader("X-MimeOLE", "Produced By Microsoft MimeOLE V6.00.2900.2869");
        mime.addHeader("ReturnReceipt", "1");
        helper.setFrom(data.getFrom());
        helper.setTo(data.getTo());
        helper.setSubject(data.getSubject());
        helper.setText(data.getContent(),true);
        mailSender.send(mime);
    }

}
