package com.blogcrawling.api.configuration;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.codahale.metrics.MetricRegistry;
import com.zaxxer.hikari.HikariDataSource;

/**
 *
 * Provides HikariCP as a primary {@link DataSource} connection pool
 *
 * @author atesel
 *
 */
@Configuration
@Profile("production")
public class HikariConfiguration {

  @Bean(destroyMethod = "close")
  public DataSource dataSource(DataSourceProperties dataSourceProperties, MetricRegistry metricRegistry) {

    @SuppressWarnings("unchecked")
    final DataSourceBuilder<HikariDataSource> dsb = (DataSourceBuilder<HikariDataSource>) DataSourceBuilder.create();
    dsb.type(HikariDataSource.class);
    dsb.driverClassName(dataSourceProperties.getDriverClassName());
    dsb.url(dataSourceProperties.getUrl());
    dsb.username(dataSourceProperties.getUsername());
    dsb.password(dataSourceProperties.getPassword());

    // TODO use HikariConfig & application.yml ??
    final HikariDataSource hikariDataSource = dsb.build();
    hikariDataSource.setMaximumPoolSize(30);
    hikariDataSource.setMetricRegistry(metricRegistry);

    return hikariDataSource;

  }

}
