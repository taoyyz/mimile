package com.taoyyz.feign.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author taoyyz
 * @since 2021-10-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String productName;

    private String productDesc;

    private BigDecimal productPrice;

    private String productPicture;

    private Long productCate;

    private Integer count;

    private boolean checked;

    private double nowPrice;

}
