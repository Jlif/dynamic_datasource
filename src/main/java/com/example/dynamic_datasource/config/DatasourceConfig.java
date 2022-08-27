package com.example.dynamic_datasource.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class DatasourceConfig {

    @Primary
    @Bean("primaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource defaultDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean("sqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactory(@Qualifier("dynamicDataSource") DynamicDataSource dynamicDataSource) throws Exception {
        //使用 MybatisSqlSessionFactoryBean 替换 SqlSessionFactoryBean，才能使用 BaseMapper
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource);
        sqlSessionFactoryBean.setPlugins(mybatisPlusInterceptor());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 指向默认的 xml 文件
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:mapper/**/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.example.dynamic_datasource.entity");
        return sqlSessionFactoryBean.getObject();
    }

    @Primary
    @Bean("transactionManager")
    public PlatformTransactionManager dynamicTransactionManager(@Qualifier("dynamicDataSource") DynamicDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}
