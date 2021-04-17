package com.wty.maill;

import com.alibaba.fastjson.JSON;
import com.wty.maill.entity.MailSend;
import com.wty.maill.enumeration.RedisPriorityQueueEnum;
import com.wty.maill.service.impl.sendredis.ReceiveRedisFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MaillConsumerApplication.class})
public class MaillConsumerApplicationTests {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ReceiveRedisFactory sendRedisFactory;

    @Test
    public void reveiceMail(){
        MailSend reveice = sendRedisFactory.reveice(RedisPriorityQueueEnum.FAST_QUEUE);
        System.out.println(JSON.toJSONString(reveice));
    }

   @Test
    public void sendMail(){
       SimpleMailMessage smm = new SimpleMailMessage();
       smm.setFrom("ant_process@aliyun.com");
//       smm.setTo("1965664138@qq.com");
       smm.setTo("279728948@qq.com");
       smm.setSubject("主题：邮箱外发服务关闭通知");
//       smm.setText("尊敬的用户：\n" +
//               "\n" +
//               "近期我们收到有关您的邮箱发送非索要广告邮件/垃圾邮件的投诉，由于您的不规范发信行为会导致邮箱IP地址存在被国际反垃圾组织列黑的风险， 影响同一IP其他所有用户的正常使用。因此非常遗憾地通知您，为了保证您和其他邮箱用户的最大利益， 现在我公司已被迫关闭了您邮箱 1965664138@aliyun.com的外发服务，该账号接收邮件暂不受影响。");
       smm.setText("测试");
       javaMailSender.send(smm);
   }

   @Test
    public void sendMimeMail() throws MessagingException {
       MimeMessage mimeMessage = javaMailSender.createMimeMessage();
       mimeMessage.addHeader("X-Mailer","Microsoft Outlook Express 6.00.2900.2869");

       MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
       helper.setFrom("ant_process@aliyun.com");
       helper.setTo("1965664138@qq.com");
       helper.setSubject("主题：邮箱外发服务关闭通知");
       String msg = "尊敬的用户：\n" +
               "\n" +
               "近期我们收到有关您的邮箱发送非索要广告邮件/垃圾邮件的投诉，由于您的不规范发信行为会导致邮箱IP地址存在被国际反垃圾组织列黑的风险， 影响同一IP其他所有用户的正常使用。因此非常遗憾地通知您，为了保证您和其他邮箱用户的最大利益， 现在我公司已被迫关闭了您邮箱 1965664138@aliyun.com的外发服务，该账号接收邮件暂不受影响。";
       helper.setText(msg);

       FileSystemResource file = new FileSystemResource(new File("D:/Exec/批准证明.txt"));
       helper.addAttachment("批准证明",file);

       javaMailSender.send(mimeMessage);

   }

}
