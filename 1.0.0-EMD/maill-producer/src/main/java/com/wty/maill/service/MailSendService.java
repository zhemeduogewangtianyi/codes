package com.wty.maill.service;


import com.wty.maill.entity.MailSend;

import java.util.List;

public interface MailSendService {

    int insert(MailSend mailSend) throws Exception;

    int updateByCondition(MailSend mailSend) throws Exception;

    List<MailSend> queryMainSendByStatus();

}
