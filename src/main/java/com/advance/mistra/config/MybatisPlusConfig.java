package com.advance.mistra.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/1/18 13:04
 * @Description: MybatisPlus启动配置类
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.advance.mistra.dao.mapper")
public class MybatisPlusConfig {

    /**
     * 数据源
     * @param
     * @return javax.sql.DataSource
     * @author Mistra
     * @date 2020/1/18 18:26
     */
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    /**
     * 事物管理器
     * @param
     * @return org.springframework.jdbc.datasource.DataSourceTransactionManager
     * @author Mistra
     * @date 2020/1/18 18:27
     */
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    /**
     * mybatis-plus 分页插件
     * @param
     * @return com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
     * @author Mistra
     * @date 2020/1/18 18:27
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }
}
