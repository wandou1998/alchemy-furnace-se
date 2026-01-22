package com.lee.af.controller;

import jdk.jfr.Enabled;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDubbo
@ComponentScan(value="com.lee.af.dubbo.service")
public class AFControllerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AFControllerApplication.class, args);
    }
}
