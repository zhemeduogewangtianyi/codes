package com.wty.maill.service.impl;

import com.alibaba.fastjson.JSON;
import com.wty.maill.annotation.DataSourceCondition;
import com.wty.maill.entity.MailSend;
import com.wty.maill.enumeration.DataSourceType;
import com.wty.maill.mapper.MailSend1Mapper;
import com.wty.maill.mapper.MailSend2Mapper;
import com.wty.maill.service.MailSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MailSendServiceImpl implements MailSendService {

    /** 日志 */
    private static final Logger LOGGER = LoggerFactory.getLogger(MailSendServiceImpl.class);

    @Resource
    private MailSend1Mapper mailSend1Mapper;

    @Resource
    private MailSend2Mapper mailSend2Mapper;

    @Override
    @DataSourceCondition
    public int insert(MailSend mailSend) throws Exception {
        LOGGER.info("新增邮件信息 {}" , JSON.toJSONString(mailSend));
        int hashCode = mailSend.getSendId().hashCode();
        int insert;
        if(conditionMapper(hashCode)){
            LOGGER.info("新增 HashCode {} , mapperSend - 1",hashCode);
            insert = mailSend1Mapper.insert(mailSend);
        }else{
            LOGGER.info("新增 HashCode {} , mapperSend - 2",hashCode);
            insert = mailSend2Mapper.insert(mailSend);
        }
        LOGGER.info("新增邮件信息 -> 返回 {}" , insert);
        return insert;
    }

    @Override
    @DataSourceCondition
    public int updateByCondition(MailSend mailSend) throws Exception {
        int res;
        int hashCode = mailSend.getSendId().hashCode();
        mailSend.setUpdateBy("修改人。。");
        mailSend.setUpdateTime(new Date());
        if(conditionMapper(hashCode)){
            LOGGER.info("更新 HashCode {} , mapperSend - 1",hashCode);
            res = mailSend1Mapper.updateByPrimaryKey(mailSend);
        }else{
            LOGGER.info("更新 HashCode {} , mapperSend - 2",hashCode);
            res = mailSend2Mapper.updateByPrimaryKey(mailSend);
        }
        LOGGER.info("更新邮件信息 -> 返回 {}" , res);
        return res;
    }

    @Override
    @DataSourceCondition(value = DataSourceType.SLAVE)
    public List<MailSend> queryMainSendByStatus() {
        List<MailSend> mailSends1 = mailSend1Mapper.queryMainSendByStatus();
        List<MailSend> mailSends2 = mailSend2Mapper.queryMainSendByStatus();
        List<MailSend> mailSends = new ArrayList<>(mailSends1.size() + mailSends2.size());
        mailSends.addAll(mailSends1);
        mailSends.addAll(mailSends2);
        return mailSends;
    }

    public boolean conditionMapper(int hashCode){
        if(hashCode % 2 == 0){
            return true;
        }
        return false;
    }

}
