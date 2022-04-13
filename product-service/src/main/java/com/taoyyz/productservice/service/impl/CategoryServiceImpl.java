package com.taoyyz.productservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taoyyz.productservice.mapper.CategoryMapper;
import com.taoyyz.productservice.model.domain.Category;
import com.taoyyz.productservice.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author taoyyz
 * @since 2021-10-20
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
