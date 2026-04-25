package com.teashop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.teashop.mapper")
public class TeaShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeaShopApplication.class, args);
    }

}