package com.example.closeup.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.tomcat.util.http.parser.HttpHeaderParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class  DataSourceConfig {
    @Bean
    public HikariDataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        dataSource.setJdbcUrl("jdbc:mysql://192.168.5.15:3306/close_up");
        dataSource.setUsername("closeup");
        dataSource.setPassword("1234");

//        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/close_up");
//        dataSource.setUsername("root");
//        dataSource.setPassword("1234");

//        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/close_up");
//        dataSource.setUsername("root");
//        dataSource.setPassword("1234");

        return dataSource;
    }
}
