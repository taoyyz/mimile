package com.taoyyz.emailservice.service.impl;

import com.taoyyz.emailservice.common.utils.MailUtils;
import com.taoyyz.emailservice.service.EmailService;
import com.taoyyz.feign.clients.UserClient;
import com.taoyyz.feign.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author taoyyz(陶俊杰)
 * @Date 2021/10/19 15:17
 * @Version 1.0
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Value("${emailUrl}")
    private String emailUrl;

    @Autowired
    private UserClient userClient;


    @Override
    public void active(String code) {
        userClient.active(code);
    }

    @Override
    public void sendMail(User user) {
        String mail = "<h1 style='text-align:center;color:indianred'>米米乐商城</h1>" +
                "<div align='center' style='font-size:20px'><a href='" + emailUrl + "/email/active/" + user.getActiveCode() +
                "'>点击激活</a>您的米米乐商城帐号：【" + user.getUsername() + "】</div>";
        //开一个线程去异步发送邮件
        new Thread(() -> MailUtils.sendMail(user.getEmail(), mail, "【米米乐商城】激活")).start();
    }
}
