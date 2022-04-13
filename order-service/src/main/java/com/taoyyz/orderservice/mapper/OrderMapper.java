package com.taoyyz.orderservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taoyyz.orderservice.model.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author taoyyz
 * @since 2021-10-20
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    @Select("SELECT order_uuid from (SELECT * from `order` WHERE uid = #{uid}) t" +
            " GROUP BY order_uuid DESC limit #{startIndex} , #{pageSize}")
    List<Order> selectByOrderUuid(Long uid, Long startIndex, Long pageSize);

    @Select("select count(1) from (SELECT 1 from (SELECT * from `order` WHERE uid = #{uid}) t " +
            "GROUP BY order_uuid DESC) t2")
    Long getTotal(Long uid);

    @Select("SELECT pid FROM `order` GROUP BY pid ORDER BY SUM(count) DESC LIMIT 7")
    List<Long> getTop7();
}
