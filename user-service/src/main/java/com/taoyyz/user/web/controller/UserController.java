package com.taoyyz.user.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.taoyyz.user.common.JsonResponse;
import com.taoyyz.user.model.domain.User;
import com.taoyyz.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


/**
 * 前端控制器
 *
 * @author taoyyz
 * @version v1.0
 * @since 2021-10-18
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 描述：根据Id 查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public JsonResponse getById(@PathVariable("id") Long id) throws Exception {
        User user = userService.getById(id);
        return JsonResponse.success(user);
    }

    @RequestMapping("/getUserById/{id}")
    public com.taoyyz.feign.domain.User findUserById(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        com.taoyyz.feign.domain.User returnUser = new com.taoyyz.feign.domain.User();
        returnUser.setAddress(user.getAddress());
        return returnUser;
    }

    /**
     * 描述：根据Id删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        userService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
     * 描述：根据Id 更新
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public JsonResponse updateUser(@PathVariable("id") Long id, User user) throws Exception {
        user.setId(id);
        userService.updateById(user);
        return JsonResponse.success(null);
    }


    /**
     * 描述:创建User
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public JsonResponse create(User user) throws Exception {
        userService.save(user);
        return JsonResponse.success(null);
    }

    /**
     * 增加用户积分
     *
     * @param uid   要增加积分的用户id
     * @param point 要增加的积分数量
     */
    @RequestMapping("/addPoint")
    void addPoint(@RequestParam("uid") Long uid, @RequestParam("point") BigDecimal point) {
        userService.addPoint(uid, point);
    }

    /**
     * 通过用户名查询用户
     *
     * @param user 要查询的用户
     * @return 查询的结果
     */
    @RequestMapping("/findUserByUsername")
    public JsonResponse findUserByUsername(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        User resUser = userService.getOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        if (resUser == null) {
            result.put("code", "001");
            result.put("msg", "用户名可用");
        } else {
            result.put("code", "0");
            result.put("msg", "用户名已存在");
        }
        return JsonResponse.success(result);
    }

    /**
     * 通过邮箱查询用户
     *
     * @param user 要查询的用户
     * @return 查询的结果
     */
    @RequestMapping("/findUserByEmail")
    public JsonResponse findUserByEmail(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        User resUser = userService.getOne(new QueryWrapper<User>().eq("email", user.getEmail()));
        if (resUser == null) {
            result.put("code", "001");
            result.put("msg", "邮箱可用");
        } else {
            result.put("code", "0");
            result.put("msg", "邮箱已存在");
        }
        return JsonResponse.success(result);
    }

    /**
     * 用户注册
     *
     * @param user 要注册的用户信息对象
     * @return 注册的结果
     */
    @RequestMapping("/register")
    public JsonResponse register(@RequestBody User user) {
        user.setPoints(BigDecimal.ZERO);
        Map<String, Object> result = userService.register(user);
        return JsonResponse.success(result);
    }

    /**
     * 用户激活
     *
     * @param code 要激活的用户码
     */
    @RequestMapping("/active/{code}")
    public void active(@PathVariable("code") String code) {
        userService.active(code);
    }

    /**
     * 用户登录
     *
     * @param loginUser 要登录的用户对象
     * @return 用户登录的结果
     */
    @RequestMapping("/login")
    public JsonResponse login(@RequestBody User loginUser) {
        Map user = userService.login(loginUser);
        return JsonResponse.success(user);
    }

    /**
     * 获取用户的折扣
     *
     * @param id 要获取折扣的用户id
     * @return 获取的折扣结果
     */
    @GetMapping("/getDiscount/{id}")
    public JsonResponse getDiscount(@PathVariable("id") Long id) {
        Map<String, Object> res = userService.getDiscount(id);
        return JsonResponse.success(res);
    }

    @RequestMapping("/addAddress")
    public JsonResponse addAddress(String address,Long id) {
        userService.updateAddress(address, id);
        return JsonResponse.success("ok");
    }
}

