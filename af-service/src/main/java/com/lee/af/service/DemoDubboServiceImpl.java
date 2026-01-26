package com.lee.af.service;

import com.lee.af.dubbo.service.DemoDubboService;
import com.lee.af.dubbo.service.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

@Slf4j
@DubboService
public class DemoDubboServiceImpl implements DemoDubboService {

    @Override
    public String sayHello(String name) {
        return "HELLO " + name;
    }

    @Override
    public UserDto getUser(UserDto user) {
        log.info("请求参数，user：{}", user.toString());
        return new UserDto(1, "苏菲");
    }
}
