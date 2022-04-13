package com.taoyyz.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taoyyz.user.model.domain.User;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author taoyyz
 * @since 2021-10-18
 */
public interface UserService extends IService<User> {

    /**
     * 注册用户
     *
     * @param user 要注册的用户
     * @return 注册的结果
     */
    Map<String, Object> register(User user);

    /**
     * 激活用户
     * @param code 要激活的激活码
     */
    void active(String code);

    /**
     * 用户登录
     * @param user 要登录的用户
     * @return 登录的结果
     */
    Map login(User user);

    /**
     * 获取用户折扣
     * @param id 要获取的用户id
     * @return 获取的结果
     */
    Map<String, Object> getDiscount(Long id);

    /**
     * 增加用户积分
     * @param uid 要增加的用户id
     * @param point 要增加的积分数量
     */
    void addPoint(Long uid, BigDecimal point);

    void updateAddress(String address, Long id);
}
