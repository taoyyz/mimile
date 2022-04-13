package com.taoyyz.orderservice;

import com.taoyyz.feign.clients.ProductClient;
import com.taoyyz.feign.clients.UserClient;
import com.taoyyz.feign.config.DefaultFeignConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.taoyyz.orderservice.mapper")
@SpringBootApplication
@EnableFeignClients(clients = {UserClient.class, ProductClient.class}, defaultConfiguration = DefaultFeignConfiguration.class)
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}