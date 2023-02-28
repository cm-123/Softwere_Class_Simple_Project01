package com.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.project.*.mapper")
public class SoftWere2classSimpleProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoftWere2classSimpleProjectApplication.class, args);
    }

}
