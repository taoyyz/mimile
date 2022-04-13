package com.taoyyz.emailservice.web.controller;

import com.taoyyz.emailservice.service.EmailService;
import com.taoyyz.feign.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author taoyyz(陶俊杰)
 * @Date 2021/10/19 15:13
 * @Version 1.0
 */
@RestController
@Slf4j
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    /**
     * 激活用户，返回成功激活页面
     *
     * @param code         要激活的用户激活码
     * @param modelAndView 返回的激活页面对象
     * @return 激活页面对象
     */
    @RequestMapping("/active/{code}")
    public ModelAndView active(@PathVariable("code") String code, ModelAndView modelAndView) {
        log.debug("返回" + code + "的激活页面");
        emailService.active(code);
        modelAndView.setViewName("active");
        return modelAndView;
    }

    /**
     * 发送激活邮件
     *
     * @param user 要发送激活邮件的用户对象
     */
    @RequestMapping("/sendRegisterEmail")
    void sendRegisterEmail(@RequestBody User user) {
        log.debug("发送注册邮件到：" + user.getEmail());
        emailService.sendMail(user);
    }
}
