package com.lee.af.controller.service;

import org.springframework.stereotype.Service;

@Service
public class Service1impl implements Service1{

    @Override
    public String sayHello(String name) {
        return "HELLO " + name;
    }
}
