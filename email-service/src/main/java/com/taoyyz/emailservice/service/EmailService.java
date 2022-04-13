package com.taoyyz.emailservice.service;

import com.taoyyz.feign.domain.User;

/**
 * @Author taoyyz(陶俊杰)
 * @Date 2021/10/19 15:17
 * @Version 1.0
 */
public interface EmailService {
    void active(String code);

    void sendMail(User user);
}
