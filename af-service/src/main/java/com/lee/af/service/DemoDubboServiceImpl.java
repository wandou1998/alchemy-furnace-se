package com.lee.af.service;

import com.lee.af.dubbo.service.DemoDubboService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@DubboService
@Service
public class DemoDubboServiceImpl implements DemoDubboService {

    @Override
    public String sayHello(String name) {
        return "HELLO " + name;
    }
}
