package com.lee.af.controller.api;

import com.lee.af.controller.annotation.Log;
import com.lee.af.controller.service.Service1;
import com.lee.af.dubbo.service.DemoDubboService;
import com.lee.af.dubbo.service.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private DemoDubboService demoDubboService;

    @Autowired
    private Service1 service1;

    @RequestMapping("/hello")
    public String hello() {
        //以下为通过CodeGeeX生成的代码
        try {
            // 1. 线程睡眠5秒
            TimeUnit.SECONDS.sleep(5);

            // 2. 执行业务逻辑
            return demoDubboService.sayHello("World");
        } catch (InterruptedException e) {
            // 恢复中断状态
            Thread.currentThread().interrupt();

            // 使用 Log4j2 (通过 Slf4j 门面) 记录错误日志
            // 记录异常堆栈信息
            log.error("线程处理被异常中断", e);

            // 根据业务需求返回错误信息
            return "Error: Processing interrupted";
        }
        /**
         * 实际代码 注释该部分

    //      return service1.sayHello("World");
       return demoDubboService.sayHello("World");
       **/
    }
//    @Log
    @GetMapping("/addLog")
    public String logRecord(@RequestParam String test) {
        log.info("addLog接口开始执行，参数：{}", test);
        return "SUCCESS";
    }

//    @Log
    @PostMapping("/addLogv2")
    public String logRecordv2(@RequestBody  UserDto dto) {
        log.info("logRecordv2接口开始执行，参数：{}", dto.toString());
        return "SUCCESS";
    }
}
