package com.taoyyz.feign.clients;

import com.taoyyz.feign.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "emailservice")
public interface EmailClient {

    /**
     * 发送激活邮件给指定用户
     *
     * @param user 要发送激活邮件的用户对象
     */
    @RequestMapping("/email/sendRegisterEmail")
    void sendRegisterEmail(@RequestBody User user);
}