package com.suixing;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@SpringBootApplication
@MapperScan("com.suixing.mapper")
public class SuixingzucheApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuixingzucheApplication.class, args);
        System.out.println( "浏览器打开：http://localhost:8089");
    }

}
