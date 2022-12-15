package com.atguigu.zhxy.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MpConfig
 * @Description TODO
 * @Author mch
 * @Date 2022/11/18
 * @Version 1.0
 */
@Configuration
@MapperScan(basePackages = "com.atguigu.zhxy.mapper")
public class MpConfig {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
