package com.qf.day84;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan(value = "com.qf.dao")
@SpringBootApplication(scanBasePackages = "com.qf")
public class Day84Application {

    public static void main(String[] args) {
        SpringApplication.run(Day84Application.class, args);
    }

}
