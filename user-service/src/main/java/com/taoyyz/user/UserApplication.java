package com.taoyyz.user;

import com.taoyyz.feign.clients.EmailClient;
import com.taoyyz.feign.clients.UserClient;
import com.taoyyz.feign.config.DefaultFeignConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.taoyyz.user.mapper")
@SpringBootApplication
@EnableFeignClients(clients = {UserClient.class, EmailClient.class}, defaultConfiguration = DefaultFeignConfiguration.class)
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
