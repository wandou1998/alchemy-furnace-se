package com.lee.af.controller.api;

import com.lee.af.controller.service.Service1;
import com.lee.af.dubbo.service.DemoDubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private DemoDubboService demoDubboService;

    @Autowired
    private Service1 service1;

    @RequestMapping("/hello")
    public String hello() {

//       return service1.sayHello("World");
        return demoDubboService.sayHello("World");
    }
}
