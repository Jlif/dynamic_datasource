package com.example.dynamic_datasource.config;

import com.example.dynamic_datasource.entity.DbConfig;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component("dynamicDataSource")
public class DynamicDataSource extends AbstractRoutingDataSource {

    private final JdbcTemplate jdbcTemplate;
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

        List<DbConfig> configList = jdbcTemplate.query("select * from db_config", new DbConfigRowMapper());
        if (!CollectionUtils.isEmpty(configList)) {
            configList.forEach(config -> {
                DataSource dataSource = DataSourceBuilder.create()
                        .url(config.getUrl())
                        .username(config.getUser())
                        .password(config.getPwd())
                        .driverClassName(config.getDriver())
                        .build();
                targetDataSources.put(String.valueOf(config.getParkId()), dataSource);
            });
        }
        targetDataSources.put("default", dataSource);
        return targetDataSources;
    }

    private static class DbConfigRowMapper implements RowMapper<DbConfig> {
        @Override
        public DbConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
            DbConfig dbConfig = new DbConfig();
            dbConfig.setId(rs.getInt("id"));
            dbConfig.setParkId(rs.getLong("park_id"));
            dbConfig.setUrl(rs.getString("url"));
            dbConfig.setUser(rs.getString("user"));
            dbConfig.setPwd(rs.getString("pwd"));
            dbConfig.setDriver(rs.getString("driver"));
            dbConfig.setCreateTime(rs.getTimestamp("create_time").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            return dbConfig;
        }
    }

}
