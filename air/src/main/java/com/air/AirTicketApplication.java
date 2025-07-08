package com.air;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.air.mapper")
@EnableTransactionManagement
public class AirTicketApplication {
    public static void main(String[] args) {
        SpringApplication.run(AirTicketApplication.class, args);
    }
} 