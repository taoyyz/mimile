package com.taoyyz.productservice.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.taoyyz.productservice.common.JsonResponse;
import com.taoyyz.productservice.model.domain.Product;
import com.taoyyz.productservice.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 前端控制器
 *
 * @author taoyyz
 * @version v1.0
 * @since 2021-10-20
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    /**
     * 描述：根据Id 查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public JsonResponse getById(@PathVariable("id") Long id) throws Exception {
        Product product = productService.getById(id);
        return JsonResponse.success(product);
    }

    /**
     * 描述：根据Id删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        productService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
     * 描述：根据Id 更新
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public JsonResponse updateProduct(@PathVariable("id") Long id, Product product) throws Exception {
        product.setId(id);
        productService.updateById(product);
        return JsonResponse.success(null);
    }


    /**
     * 描述:创建Product
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public JsonResponse create(Product product) throws Exception {
        productService.save(product);
        return JsonResponse.success(null);
    }

    @RequestMapping("/getAllProduct")
    public JsonResponse getAllProduct(Long currentPage, Long pageSize) {
        Map<String, Object> result = productService.listByPage(currentPage, pageSize, null);
        return JsonResponse.success(result);
    }

    @GetMapping("/getByFeign/{id}")
    Product findById(@PathVariable("id") Long id) {
        return productService.getById(id);
    }

    /**
     * 根据商品的分类id查询所属分类的商品
     *
     * @param cateId      要查询的商品的分类id
     * @param currentPage 当前页码
     * @param pageSize    分页大小
     * @return 查询到的数据
     */
    @RequestMapping("/getProductByCategory")
    public JsonResponse getProductByCategory(@RequestParam("id") Long cateId, Long currentPage, Long pageSize) {
        Map<String, Object> result = productService.listByPage(currentPage, pageSize, new QueryWrapper<Product>().eq("product_cate", cateId));
        return JsonResponse.success(result);
    }

    @RequestMapping("/getProductBySearch")
    public JsonResponse getProductBySearch(@RequestParam("search") String search, Long currentPage, Long pageSize) {
        Map<String, Object> result = productService.listByPage(currentPage, pageSize, new QueryWrapper<Product>().like("product_name", search));
        return JsonResponse.success(result);
    }

    /**
     * 获取热门水果，显示销量前6个
     *
     * @return 查询到的热门水果结果
     */
    @RequestMapping("/getHot")
    public JsonResponse getHot() {
        List<Product> fruitList = productService.getTop7();
        return JsonResponse.success(fruitList);
    }
}

