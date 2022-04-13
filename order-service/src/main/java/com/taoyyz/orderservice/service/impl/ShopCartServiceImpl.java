package com.taoyyz.orderservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoyyz.feign.clients.ProductClient;
import com.taoyyz.feign.domain.Product;
import com.taoyyz.orderservice.mapper.ShopCartMapper;
import com.taoyyz.orderservice.model.domain.ShopCart;
import com.taoyyz.orderservice.service.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
public class ShopCartServiceImpl extends ServiceImpl<ShopCartMapper, ShopCart> implements ShopCartService {

    @Autowired
    private ShopCartMapper mapper;

    @Autowired
    private ProductClient productClient;

    @Override
    public Map<String, Object> createShopCart(ShopCart shopCart) {
        Map<String, Object> res = new HashMap<>();
        //判断是否已经加入过购物车
        ShopCart haveThis = mapper.selectOne(new QueryWrapper<ShopCart>()
                .eq("uid", shopCart.getUid())
                .eq("pid", shopCart.getPid()));
        if (haveThis == null) {
            //没有加入过购物车，相当于新加入购物车
            //记得加入购物车之后还要把此商品信息给前端
            res.put("code", "001");
            res.put("msg", "加入购物车成功");
            res.put("shoppingCartData", productClient.findById(shopCart.getPid()));
            mapper.insert(shopCart);
        } else {
            //已经加入过购物车了，相当于购买数量加1
            res.put("code", "002");
            res.put("msg", "购买数量+1");
            mapper.addNum(shopCart);
        }
        return res;
    }

    @Override
    public Map listShopCartByUserId(Long uid) {
        Map<String, Object> res = new HashMap<>();
        List<Object> productList = new ArrayList<>();
        //查询此id的user的所有购物车情况
        try {
            List<ShopCart> shopCartList = mapper.selectList(new QueryWrapper<ShopCart>()
                    .eq("uid", uid));
            //查询购物车中每个商品的信息
            for (ShopCart shopCart : shopCartList) {
                Product product = productClient.findById(shopCart.getPid());
                product.setCount(shopCart.getCount());
                productList.add(product);
            }
            res.put("productList", productList);
            res.put("code", "001");
            res.put("msg", "获取购物车成功");
        } catch (Exception e) {
            res.put("productList", null);
            res.put("code", "0");
            res.put("msg", "获取购物车失败");
        }
        return res;
    }
}
