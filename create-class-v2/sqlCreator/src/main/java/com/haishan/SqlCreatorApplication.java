package com.haishan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.ConfigurableApplicationContext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

@MapperScan(basePackages = "com.haishan.dao")
public class SqlCreatorApplication {


	public static ConfigurableApplicationContext CONTEXT;
    public static void main(String[] args) {
		CONTEXT = SpringApplication.run(SqlCreatorApplication.class,args);
    }

}