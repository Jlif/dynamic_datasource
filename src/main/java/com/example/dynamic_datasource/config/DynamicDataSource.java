package com.example.dynamic_datasource.config;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component("dynamicDataSource")
public class DynamicDataSource extends AbstractRoutingDataSource {

    private final DataSource dataSource;

    @Override
    protected Object determineCurrentLookupKey() {
        return ThreadLocalDataSourceContext.getLocalDataSource();
    }

    @Override
    public void afterPropertiesSet() {
        //为 targetDataSources 设置数据源集合
        super.setTargetDataSources(getAllDataSourceFromDb());

        //为 defaultTargetDataSource 设置默认数据源
        super.setDefaultTargetDataSource(dataSource);

        super.afterPropertiesSet();
    }

    private Map<Object, Object> getAllDataSourceFromDb() {
        Map<Object, Object> targetDataSources = Maps.newHashMapWithExpectedSize(16);
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("select * from db_config");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DataSource dataSource = DataSourceBuilder.create()
                        .url(rs.getString(3))
                        .username(rs.getString(4))
                        .password(rs.getString(5))
                        .driverClassName(rs.getString(6))
                        .build();
                targetDataSources.put(String.valueOf(rs.getString(2)), dataSource);
            }
            targetDataSources.put("default", dataSource);
        } catch (SQLException e) {
            throw new RuntimeException("从默认数据库加载租户数据库配置异常");
        }
        return targetDataSources;
    }

}
