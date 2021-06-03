package com.example.datasources;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.example.datasources.dao")
public class DatasourcesApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatasourcesApplication.class, args);
    }

}
