package com.taoyyz.productservice;

import com.taoyyz.feign.clients.OrderClient;
import com.taoyyz.feign.clients.ProductClient;
import com.taoyyz.feign.config.DefaultFeignConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.taoyyz.productservice.mapper")
@EnableFeignClients(clients = {ProductClient.class, OrderClient.class}, defaultConfiguration = DefaultFeignConfiguration.class)
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

}
