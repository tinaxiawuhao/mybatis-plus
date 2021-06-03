package com.example.datasources.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * spring的jdbc提供了动态数据源的入口
 * 继承AbstractRoutingDataSource覆盖determineCurrentLookupKey()方法 返回当前使用数据库
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        log.info("当前使用数据库：{}", DbUtil.getDb());
        return DbUtil.getDb();
    }
}