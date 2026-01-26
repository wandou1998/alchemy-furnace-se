package com.lee.af.controller;

import jdk.jfr.Enabled;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication(scanBasePackages = {"com.lee.af"})
@EnableDubbo
//@ComponentScan(basePackages = {"com.lee.af.service","com.lee.af.controller"})
public class AFControllerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AFControllerApplication.class, args);
    }



}
