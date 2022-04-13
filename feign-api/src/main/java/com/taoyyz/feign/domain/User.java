package com.taoyyz.feign.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author taoyyz
 * @since 2021-10-23
 */
@Data
public class User {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String password;

    private String email;

    private String address;

    private LocalDateTime createTime;

    private BigDecimal points;

    private String activeStatus;

    private String activeCode;


}
