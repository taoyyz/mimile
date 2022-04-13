package com.taoyyz.productservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taoyyz.feign.clients.OrderClient;
import com.taoyyz.productservice.mapper.ProductMapper;
import com.taoyyz.productservice.model.domain.Product;
import com.taoyyz.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author taoyyz
 * @since 2021-10-20
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Autowired
    private ProductMapper mapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderClient orderClient;

    @Override
    public Map listByPage(Long currentPage, Long pageSize, QueryWrapper<Product> wrapper) {
        Page<Product> page = new Page<>(currentPage, pageSize);
        IPage<Product> iPage = mapper.selectPage(page, wrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("Product", iPage.getRecords());
        result.put("total", iPage.getTotal());
        return result;
    }

    @Override
    public List<Product> getTop7() {
        //已经存在redis缓存
        if (redisTemplate.opsForHash().hasKey("hotProducts", "top7")) {
            List<Product> fruitList = new ArrayList<>();
            Object o = redisTemplate.opsForHash().get("hotProducts", "top7");
            try {
                fruitList = objectMapper.readValue((String) o, List.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return fruitList;
        } else {
            //否则不存在redis缓存
            List<Product> fruitList = new ArrayList<>();
            //通过feign查询销量前七的商品id
            List<Long> top6Id = orderClient.getTop7Id();
            //把每个id对应的商品的信息添加到fruitList
            top6Id.forEach(id -> fruitList.add(mapper.selectById(id)));
            try {
                //存入redis，利用objectMapper序列化为字符串
                redisTemplate.opsForHash().put("hotProducts", "top7", objectMapper.writeValueAsString(fruitList));
                //搞个5秒有效期，也就是热门商品5秒更新一次
                redisTemplate.expire("hotProducts", 5, TimeUnit.SECONDS);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return fruitList;
        }
    }
}
