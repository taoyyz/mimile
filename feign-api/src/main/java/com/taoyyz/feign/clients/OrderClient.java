package com.taoyyz.feign.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "orderservice")
public interface OrderClient {

    @RequestMapping("/order/getTop7Id")
    List<Long> getTop7Id();
}