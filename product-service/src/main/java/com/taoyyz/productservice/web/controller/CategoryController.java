package com.taoyyz.productservice.web.controller;

import com.taoyyz.productservice.common.JsonResponse;
import com.taoyyz.productservice.model.domain.Category;
import com.taoyyz.productservice.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 前端控制器
 *
 * @author taoyyz
 * @version v1.0
 * @since 2021-10-20
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    /**
     * 描述：根据Id 查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public JsonResponse getById(@PathVariable("id") Long id) throws Exception {
        Category category = categoryService.getById(id);
        return JsonResponse.success(category);
    }

    /**
     * 描述：根据Id删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        categoryService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
     * 描述：根据Id 更新
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public JsonResponse updateCategory(@PathVariable("id") Long id, Category category) throws Exception {
        category.setId(id);
        categoryService.updateById(category);
        return JsonResponse.success(null);
    }


    /**
     * 描述:创建Category
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public JsonResponse create(Category category) throws Exception {
        categoryService.save(category);
        return JsonResponse.success(null);
    }

    @RequestMapping("/getAllCategory")
    public JsonResponse getAll() {
        return JsonResponse.success(categoryService.list());
    }
}

