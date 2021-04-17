package com.wty.maill.service;


import com.wty.maill.entity.MailSend;
import com.wty.maill.entity.vo.MailDataVO;

import javax.mail.MessagingException;

public interface MailSendService {

    int updateMailStatus(MailSend mailSend);

    MailDataVO processMessage(MailSend mailSend);

    void sendMessage(MailDataVO data) throws MessagingException;

}
