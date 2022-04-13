package com.taoyyz.orderservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taoyyz.orderservice.model.domain.ShopCart;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author taoyyz
 * @since 2021-10-20
 */
public interface ShopCartService extends IService<ShopCart> {

    Map<String, Object> createShopCart(ShopCart shopCart);

    Map listShopCartByUserId(Long id);
}
