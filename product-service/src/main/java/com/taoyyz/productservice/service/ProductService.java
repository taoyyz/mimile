package com.taoyyz.productservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taoyyz.productservice.model.domain.Product;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author taoyyz
 * @since 2021-10-20
 */
public interface ProductService extends IService<Product> {

    Map<String, Object> listByPage(Long currentPage, Long pageSize, QueryWrapper<Product> wrapper);

    List<Product> getTop7();
}
