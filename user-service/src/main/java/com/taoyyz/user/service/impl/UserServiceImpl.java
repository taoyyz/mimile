package com.taoyyz.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoyyz.feign.clients.EmailClient;
import com.taoyyz.user.common.utils.UuidUtil;
import com.taoyyz.user.mapper.UserMapper;
import com.taoyyz.user.model.domain.User;
import com.taoyyz.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author taoyyz
 * @since 2021-10-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper mapper;

    @Autowired
    private EmailClient emailClient;

    /**
     * 新用户注册
     *
     * @param user 要注册的用户
     * @return 注册的结果信息
     */
    @Override
    public Map<String, Object> register(User user) {
        //为用户设置唯一UUID
        user.setActiveCode(UuidUtil.getUuid());
        //保存用户到数据库
        mapper.insert(user);
        //发送邮箱验证
        //这里的User对象要使用feign-api模块里的User对象
        com.taoyyz.feign.domain.User registerUser = new com.taoyyz.feign.domain.User();
        registerUser.setUsername(user.getUsername());
        registerUser.setActiveCode(user.getActiveCode());
        registerUser.setEmail(user.getEmail());
        emailClient.sendRegisterEmail(registerUser);
        //返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("code", "001");
        result.put("msg", "注册成功，请查看邮件激活账户");
        return result;
    }

    /**
     * 用户激活，修改激活状态为Y
     *
     * @param code 用户唯一的UUID激活码
     */
    @Override
    public void active(String code) {
        mapper.updateByActiveCode(code);
    }

    /**
     * 用户登录
     *
     * @param user 要登录的用户信息
     * @return 是否登录成功的提示信息
     */
    @Override
    public Map login(User user) {
        Map<String, Object> result = new HashMap<>();
        //查看用户名或密码是否正确
        User exist = mapper.getUserByUsernameOrEmail(user);
        if (exist == null) {
            result.put("code", 1);
            result.put("msg", "用户名或密码不正确");
        } else if (exist.getActiveStatus().equals("Y")) {
            result.put("code", "001");
            result.put("user", exist);
            result.put("msg", "登录成功");
        } else {
            //没有激活
            result.put("code", 1);
            result.put("msg", "请先根据邮件提示激活账户");
        }
        return result;
    }

    /**
     * 获取此id用户的折扣
     *
     * @param id 要获取折扣的用户id
     * @return 会员等级名和折扣值
     */
    @Override
    public Map getDiscount(Long id) {
        Map<String, Object> res = new HashMap<>();
        //根据用户id获取折扣
        User user = mapper.selectOne(new QueryWrapper<User>()
                .eq("id", id));
        //获取积分对应的折扣
        if (user.getPoints().longValue() >= 0 && user.getPoints().longValue() < 50) { //普通用户
            res.put("gradeName", "普通用户");
            res.put("discount", 1);
        } else if (user.getPoints().longValue() >= 50 && user.getPoints().longValue() < 200) { //银米
            res.put("gradeName", "银米");
            res.put("discount", 0.97);
        } else if (user.getPoints().longValue() >= 200 && user.getPoints().longValue() < 500) { //金米
            res.put("gradeName", "金米");
            res.put("discount", 0.95);
        } else if (user.getPoints().longValue() >= 500 && user.getPoints().longValue() < 1000) { //铂金米
            res.put("gradeName", "铂金米");
            res.put("discount", 0.9);
        } else if (user.getPoints().longValue() >= 1000 && user.getPoints().longValue() < 5000) { //钻石米
            res.put("gradeName", "钻石米");
            res.put("discount", 0.85);
        } else if (user.getPoints().longValue() >= 5000) { //至尊钻石米
            res.put("gradeName", "至尊钻石米");
            res.put("discount", 0.8);
        }
        return res;
    }

    /**
     * 增加用户积分
     *
     * @param uid   要增加积分的用户id
     * @param point 要增加的积分数
     */
    @Override
    public void addPoint(Long uid, BigDecimal point) {
        mapper.addPoint(uid, point);
    }

    /**
     * 更新指定id的用户地址
     * @param address 新地址
     * @param id 要更新的用户id
     */
    @Override
    public void updateAddress(String address, Long id) {
        User user = new User();
        user.setId(id);
        user.setAddress(address);
        mapper.updateById(user);
    }
}
