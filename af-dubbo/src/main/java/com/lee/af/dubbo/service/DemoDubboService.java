package com.lee.af.dubbo.service;


import com.lee.af.dubbo.service.dto.UserDto;

public interface DemoDubboService {

    public String sayHello(String name);

    public UserDto getUser(UserDto user);
}
