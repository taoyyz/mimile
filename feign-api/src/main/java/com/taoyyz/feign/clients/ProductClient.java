package com.taoyyz.feign.clients;

import com.taoyyz.feign.domain.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "productservice")
public interface ProductClient {

    /**
     * 通过商品id查找商品对象
     *
     * @param id 要查找的商品id
     * @return 查找到的商品对象
     */
    @GetMapping("/product/getByFeign/{id}")
    Product findById(@PathVariable("id") Long id);
}