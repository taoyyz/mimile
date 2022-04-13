package com.taoyyz.orderservice.web.controller;

import com.taoyyz.feign.clients.ProductClient;
import com.taoyyz.orderservice.common.JsonResponse;
import com.taoyyz.orderservice.model.domain.Order;
import com.taoyyz.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 前端控制器
 *
 * @author taoyyz
 * @version v1.0
 * @since 2021-10-20
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductClient productClient;

    /**
     * 描述：根据Id 查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public JsonResponse getById(@PathVariable("id") Long id) throws Exception {
        Order order = orderService.getById(id);
        return JsonResponse.success(order);
    }

    /**
     * 描述：根据Id删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        orderService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
     * 描述：根据Id 更新
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public JsonResponse updateOrder(@PathVariable("id") Long id, Order order) throws Exception {
        order.setId(id);
        orderService.updateById(order);
        return JsonResponse.success(null);
    }


    /**
     * 描述:创建Order
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public JsonResponse create(Order order) throws Exception {
        orderService.save(order);
        return JsonResponse.success(null);
    }

    /**
     * 获得该uid用户的所有订单记录 <br>
     * 这些记录被分组查询出来并<b>分组</b>存放到一个泛型为List的List集合中 <br>
     * 同样的，这些订单对应的商品记录也被<b>分组</b>存放到一个泛型为List的List集合 <br>
     *
     * @param uid 要查询订单的用户id
     * @return 查询到的结果或错误码
     */
    @RequestMapping("/listOrderByPage")
    public JsonResponse getAll(@RequestParam("uid") Long uid,
                               @RequestParam("currentPage") Long currentPage,
                               @RequestParam("pageSize") Long pageSize) {
        Map<String, Object> res = new HashMap<>();
        try {
            List<List<Order>> ordersList = orderService.getOrderByUidGroupByOrderUuid(uid, currentPage, pageSize);
            //放入订单列表
            res.put("ordersList", ordersList);
            //放入商品列表
            res.put("productsList", orderService.getProductByOrdersList(ordersList));
            //放入总共条数total
            res.put("total", orderService.getTotal(uid));
            res.put("code", "001");
        } catch (Exception e) {
            e.printStackTrace();
            res.put("code", "002");
            res.put("msg", "获取订单列表失败");
        }
        return JsonResponse.success(res);
    }


    /**
     * 添加订单记录
     *
     * @param params 要添加的订单记录数据，包括了：<br>
     *               添加此订单的用户uid <br>
     *               要添加的订单列表：包括商品id和价格折扣等信息
     * @return 添加订单的结果是否正确的代码
     */
    @RequestMapping("/addOrder")
    public JsonResponse addOrder(@RequestBody Map params) {
        Map<String, Object> res = new HashMap<>();
        try {
            orderService.addOrder(params);
            res.put("code", "001");
        } catch (Exception e) {
            e.printStackTrace();
            res.put("code", "500");
        }
        return JsonResponse.success(res);
    }

    /**
     * 获取销量前6的水果id
     *
     * @return 前6的水果id
     */
    @RequestMapping("/getTop7Id")
    List<Long> getTop7Id() {
        return orderService.getTop7Id();
    }
}

