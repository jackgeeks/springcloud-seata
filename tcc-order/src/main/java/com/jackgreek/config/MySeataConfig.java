package com.jackgreek.config;
import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import io.seata.rm.datasource.xa.DataSourceProxyXA;
import io.seata.spring.annotation.GlobalTransactionScanner;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;


/**
 * @Author: jackgeeks
 * @ProjectName: gulishop
 * @Package: com.jackgreek.common.config
 * @ClassName: SwaggerConfiguration
 * @Description: @todo
 * @CreateDate: 2020/12/27 3:36
 * @Version: 1.0
 */

@Configuration
public class MySeataConfig {

    @Autowired
    DataSourceProperties dataSourceProperties;
    @Bean
    public DataSource dataSource(DataSourceProperties dataSourceProperties) {

        HikariDataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        if (StringUtils.hasText(dataSourceProperties.getName())) {
            dataSource.setPoolName(dataSourceProperties.getName());
        }
//        //AT 代理
//        return new DataSourceProxy(dataSource);
        //XA代理
        return new DataSourceProxyXA(dataSource);
    }

}