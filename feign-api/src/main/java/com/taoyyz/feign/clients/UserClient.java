package com.taoyyz.feign.clients;

import com.taoyyz.feign.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(value = "userservice")
public interface UserClient {

    /**
     * 通过id查找用户对象
     *
     * @param id 要查找的用户的id
     * @return 查找到的用户对象
     */
    @GetMapping("/user/{id}")
    User findById(@PathVariable("id") Long id);

    /**
     * 激活用户
     *
     * @param code 要激活的用户的激活码
     */
    @RequestMapping("/user/active/{code}")
    void active(@PathVariable("code") String code);

    /**
     * 通过用户id查找用户
     *
     * @param uid 要查找的用户id
     * @return 查找到的用户对象
     */
    @GetMapping("/user/getUserById/{id}")
    User findUserById(@PathVariable("id") Long uid);

    /**
     * 增加指定id用户的积分
     *
     * @param uid   要增加积分的用户id
     * @param point 要增加的积分数量
     */
    @RequestMapping("/user/addPoint")
    void addPoint(@RequestParam("uid") Long uid, @RequestParam("point") BigDecimal point);
}