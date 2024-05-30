package com.communify.global.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class DatasourceConfig {

    @Bean("sourceDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.source")
    public DataSource sourceDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean("replicaDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.replica")
    public DataSource replicaDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean("routingDataSource")
    public DataSource routingDataSource(@Qualifier("sourceDataSource") DataSource sourceDataSource,
                                        @Qualifier("replicaDataSource") DataSource replicaDataSource) {

        AbstractRoutingDataSource routingDatasource = new AbstractRoutingDataSource() {

            @Override
            protected Object determineCurrentLookupKey() {
                boolean isCurrentTransactionReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();

                return isCurrentTransactionReadOnly ? DataSourceType.REPLICA : DataSourceType.SOURCE;
            }
        };

        Map<Object, Object> targetDatasourceMap = new HashMap<>(2);
        targetDatasourceMap.put(DataSourceType.SOURCE, sourceDataSource);
        targetDatasourceMap.put(DataSourceType.REPLICA, replicaDataSource);

        routingDatasource.setDefaultTargetDataSource(sourceDataSource);
        routingDatasource.setTargetDataSources(targetDatasourceMap);

        return routingDatasource;
    }

    @Primary
    @Bean
    public DataSource proxyDataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    private enum DataSourceType {
        SOURCE, REPLICA
    }
}
