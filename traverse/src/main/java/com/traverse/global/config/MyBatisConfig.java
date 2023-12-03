package com.traverse.global.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.traverse.query.domain.*.mapper")
public class MyBatisConfig {
}
