package com.taoyyz.orderservice.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.taoyyz.orderservice.common.JsonResponse;
import com.taoyyz.orderservice.model.domain.ShopCart;
import com.taoyyz.orderservice.service.ShopCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * 前端控制器
 *
 * @author taoyyz
 * @version v1.0
 * @since 2021-10-20
 */
@RestController
@RequestMapping("/shopCart")
public class ShopCartController {

    private final Logger logger = LoggerFactory.getLogger(ShopCartController.class);

    @Autowired
    private ShopCartService shopCartService;

    /**
     * 描述：根据Id 查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public JsonResponse getById(@PathVariable("id") Long id) throws Exception {
        ShopCart shopCart = shopCartService.getById(id);
        return JsonResponse.success(shopCart);
    }

    /**
     * 描述：根据Id删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        shopCartService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
     * 描述：根据Id 更新
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public JsonResponse updateShopCart(@PathVariable("id") Long id, ShopCart shopCart) throws Exception {
        shopCart.setId(id);
        shopCartService.updateById(shopCart);
        return JsonResponse.success(null);
    }


    /**
     * 描述:创建ShopCart
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public JsonResponse create(@RequestBody ShopCart shopCart) throws Exception {
        Map<String, Object> result = shopCartService.createShopCart(shopCart);
        return JsonResponse.success(result);
    }

    /**
     * 通过id获取购物车列表
     *
     * @param uid 要获取购物车列表的用户id
     * @return 获取的购物车列表
     */
    @RequestMapping("/listShopCartById/{id}")
    public JsonResponse listShopCartByUid(@PathVariable("id") String uid) {
        return JsonResponse.success(shopCartService.listShopCartByUserId(Long.valueOf(uid)));
    }

    /**
     * 通过uid和pid删除购物车记录
     *
     * @param shopCart 要删除的购物车对象
     * @return 删除的结果
     */
    @RequestMapping("/deleteByUidAndPid")
    public JsonResponse deleteByUidAndPid(@RequestBody ShopCart shopCart) {
        boolean remove = shopCartService.remove(new QueryWrapper<ShopCart>()
                .eq("uid", shopCart.getUid()).eq("pid", shopCart.getPid()));
        Map<String, Object> map = new HashMap<>();
        map.put("code", remove ? "001" : "0");
        map.put("msg", remove ? "从购物车删除成功" : "删除失败");
        return JsonResponse.success(map);
    }

    /**
     * 修改购物车的商品数量
     *
     * @param shopCart 要修改的购物车对象
     * @return 修改的结果
     */
    @RequestMapping("/updateShoppingCart")
    public JsonResponse updateShoppingCart(@RequestBody ShopCart shopCart) {
        shopCartService.update(shopCart, new QueryWrapper<ShopCart>().eq("uid", shopCart.getUid()).eq("pid", shopCart.getPid()));
        Map<String, Object> res = new HashMap<>();
        res.put("code", "001");
        res.put("msg", "修改数量成功");
        return JsonResponse.success(res);
    }
}

