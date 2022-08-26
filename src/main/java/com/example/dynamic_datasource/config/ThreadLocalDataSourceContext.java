package com.example.dynamic_datasource.config;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jiangchen
 * @since 2022-08-25
 */
@Slf4j
public class ThreadLocalDataSourceContext {

    //使用threadLocal保证切换数据源时的线程安全 不会在多线程的情况下导致切换错数据源
    public static final ThreadLocal<String> DataSourceKey = new ThreadLocal<>();

    /**
     * 修改当前线程内的数据源id
     */
    public static void setLocalDataSource(String key) {
        DataSourceKey.set(key);
    }

    /**
     * 获取当前线程内的数据源类型
     */
    public static String getLocalDataSource() {
        return DataSourceKey.get();
    }

    /**
     * 清空ThreadLocal中的DataSourceContext
     */
    public static void clear() {
        DataSourceKey.remove();
    }

}
