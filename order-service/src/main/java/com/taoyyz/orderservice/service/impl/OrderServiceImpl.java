package com.taoyyz.orderservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taoyyz.feign.clients.ProductClient;
import com.taoyyz.feign.clients.UserClient;
import com.taoyyz.feign.domain.Product;
import com.taoyyz.orderservice.mapper.OrderMapper;
import com.taoyyz.orderservice.model.domain.Order;
import com.taoyyz.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author taoyyz
 * @since 2021-10-20
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserClient userClient;
    @Autowired
    private ProductClient productClient;

    /**
     * 根据用户下单的商品列表增加订单记录
     *
     * @param paramsMap 要增加的订单记录包含的商品和下单的用户
     */
    @Override
    public void addOrder(Map paramsMap) {
        //这个(List) paramsMap.get("productArr");的List里装的是键值对均为实体类属性的Map
        //获得用户uid
        Long uid = ((Integer) paramsMap.get("uid")).longValue();
        //这个discount在不打折（也就是为1）的时候是Integer类型，否则为Double类型
        Object discount1 = paramsMap.get("discount");
        Integer discount = 0;
        if (discount1 instanceof Double) {
            discount = (int) ((Double) discount1 * 100);
        } else if (discount1 instanceof Integer) {
            discount = (Integer) discount1 * 100;
        }
        //获得购买的商品列表
        List productList = (List) paramsMap.get("productArr");
        //产生一个当前毫秒值的唯一uuid作为此用户此次订单的唯一标识
        String uuid = String.valueOf(System.currentTimeMillis());
        BigDecimal point = new BigDecimal(0);
        for (Object productJSON : productList) {
            //利用ObjectMapper把JSON格式的Map解析成Product对象
            Product product = objectMapper.convertValue(productJSON, Product.class);
            Order order = new Order();
            order.setUid(uid);
            order.setPid(product.getId());
            order.setDiscount(discount);
            order.setOrderPrice(product.getProductPrice().multiply(BigDecimal.valueOf(product.getCount().longValue())));
            //通过uid从UserClient获取用户当前最新设置的地址
            order.setOrderAddress(userClient.findUserById(uid).getAddress());
            order.setCount(product.getCount());
            //给此次订单设置一个唯一标识
            order.setOrderUuid(uuid + "_" + uid);
            orderMapper.insert(order);
            BigDecimal nowPrice = BigDecimal.valueOf(product.getNowPrice());
            BigDecimal price = nowPrice.multiply(BigDecimal.valueOf(product.getCount()));
            point = point.add(price);
        }
        //增加用户对应的积分
        userClient.addPoint(uid, point);
    }

    @Override
    public List<List<Order>> getOrderByUidGroupByOrderUuid(Long uid, Long currentPage, Long pageSize) {
        //根据用户uid，分组查询此用户所有的订单组
        Long startIndex = (currentPage - 1) * pageSize;
        List<Order> orderGroup = orderMapper.selectByOrderUuid(uid, startIndex, pageSize);
        //根据分组，查询每个分组下的订单
        List<List<Order>> ordersList = new ArrayList<>();
        for (Order order : orderGroup) {
            ordersList.add(orderMapper.selectList(new QueryWrapper<Order>().eq("order_uuid", order.getOrderUuid())));
        }
        return ordersList;
    }

    @Override
    public Long getTotal(Long uid) {
        return orderMapper.getTotal(uid);
    }

    @Override
    public List<List<Product>> getProductByOrdersList(List<List<Order>> ordersList) {
        List<List<Product>> productsList = new ArrayList<>();
        //遍历订单列表中的每一条订单
        for (int i = 0; i < ordersList.size(); i++) {
            //每一条订单都作为一个List<Product>添加到总的productsList中
            productsList.add(new ArrayList<>());
            for (int j = 0; j < ordersList.get(i).size(); j++) {
                //把每条order对应的商品Product对象查出来
                Product product = productClient.findById(ordersList.get(i).get(j).getPid());
                productsList.get(i).add(product);
            }
        }
        return productsList;
    }

    @Override
    public List<Long> getTop7Id() {
        return orderMapper.getTop7();
    }
}
