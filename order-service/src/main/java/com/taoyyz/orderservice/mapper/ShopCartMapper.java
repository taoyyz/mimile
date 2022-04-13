package com.taoyyz.orderservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taoyyz.orderservice.model.domain.ShopCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author taoyyz
 * @since 2021-10-20
 */
@Mapper
public interface ShopCartMapper extends BaseMapper<ShopCart> {

    @Update("UPDATE shop_cart SET `count` = " +
            "(select a.count from (select `count` from shop_cart WHERE uid = #{uid} and pid = #{pid}) a) + 1 " +
            "WHERE uid = #{uid} and pid = #{pid}")
    void addNum(ShopCart shopCart);
}
