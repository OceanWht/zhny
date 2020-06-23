package com.xl.ems.ynnhjc.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidConfig {

    @Bean(initMethod = "init",destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.druid")
    public DruidDataSource dataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setProxyFilters(Lists.newArrayList(statFilter()));

        return druidDataSource;
    }


    @Bean
    public Filter statFilter(){
        StatFilter statFilter = new StatFilter();
        //是否打印日志
        statFilter.setLogSlowSql(true);
        //执行sql多少秒为慢sql
        statFilter.setSlowSqlMillis(5000);

        //是否合并日志
        statFilter.setMergeSql(true);

        return statFilter;
    }


    /**
     * 添加监控配置
     * @return
     */
    @Bean
    public ServletRegistrationBean registrationBean(){
        return new ServletRegistrationBean(new StatViewServlet(),"/durid/*");
    }
}
