package com.zty;

import com.zty.protocol.HttpServer;
import com.zty.register.LocalRegister;
import com.zty.remote.HelloService;
import com.zty.service.HelloServiceImpl;

public class Provider {
    public static void main(String[] args) {
        //将提供远程调用的方法进行注册
        LocalRegister.regist(HelloService.class.getName(), HelloServiceImpl.class);

        HttpServer httpServer = new HttpServer();
        httpServer.start("localhost",8080);


    }
}
