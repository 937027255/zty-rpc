package com.zty.service;

import com.zty.remote.HelloService;

public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        return "say hello to" + name;

    }
}
