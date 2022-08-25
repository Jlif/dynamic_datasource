package com.example.dynamic_datasource.config;

import com.google.common.collect.Maps;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class DynamicDataSource extends AbstractRoutingDataSource {

    private final DataSource dataSource;

    @Override
    protected Object determineCurrentLookupKey() {
        return ThreadLocalDataSourceContext.getLocalDataSource();
    }

    @Override
    public void afterPropertiesSet() {
        //为 targetDataSources 设置数据源集合
        Map<Object, Object> targetDataSources = Maps.newHashMap();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("select * from db_config");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HikariDataSource dataSource = new HikariDataSource();
                dataSource.setJdbcUrl(rs.getString(3));
                dataSource.setUsername(rs.getString(4));
                dataSource.setPassword(rs.getString(5));
                dataSource.setDriverClassName(rs.getString(6));
                targetDataSources.put(String.valueOf(rs.getString(2)), dataSource);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.setTargetDataSources(targetDataSources);

        //为 defaultTargetDataSource 设置默认数据源
        super.setDefaultTargetDataSource(dataSource);

        super.afterPropertiesSet();
    }
}
