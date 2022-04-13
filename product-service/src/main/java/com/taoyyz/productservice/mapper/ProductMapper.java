package com.taoyyz.productservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taoyyz.productservice.model.domain.Product;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author taoyyz
 * @since 2021-10-20
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

}
