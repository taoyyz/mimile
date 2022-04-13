package com.taoyyz.orderservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taoyyz.feign.domain.Product;
import com.taoyyz.orderservice.model.domain.Order;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author taoyyz
 * @since 2021-10-20
 */
public interface OrderService extends IService<Order> {

    /**
     * 新增订单记录
     *
     * @param productArr 要新增的订单的信息
     */
    void addOrder(Map productArr);

    /**
     * 通过订单的uuid分组分页查询指定用户id的订单
     *
     * @param uid         要查询订单的用户id
     * @param currentPage 当前页码
     * @param pageSize    分页大小
     * @return 分页查询的结果
     */
    List<List<Order>> getOrderByUidGroupByOrderUuid(Long uid, Long currentPage, Long pageSize);

    /**
     * 获取指定id的用户的所有订单数量
     *
     * @param uid 要获取订单数量的id
     * @return 查询到的订单数量
     */
    Long getTotal(Long uid);

    List<List<Product>> getProductByOrdersList(List<List<Order>> ordersList);

    List<Long> getTop7Id();
}
