package com.taoyyz.emailservice.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * @Author taoyyz(陶俊杰)
 * @Date 2021/10/19 15:17
 * @Version 1.0
 */
@Mapper
public interface EmailMapper {


    void active();
}
