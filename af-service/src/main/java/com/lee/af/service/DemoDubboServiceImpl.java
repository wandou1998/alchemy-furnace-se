package com.lee.af.service;

import com.lee.af.dubbo.service.DemoDubboService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class DemoDubboServiceImpl implements DemoDubboService {

    @Override
    public String sayHello(String name) {
        return "HELLO " + name;
    }
}
