package com.zty;

import com.zty.common.Invocation;
import com.zty.common.ProxyFactory;
import com.zty.protocol.HttpClient;
import com.zty.protocol.HttpServer;
import com.zty.remote.HelloService;

public class Consumer {
    public static void main(String[] args) {
        //使用代理类和范型方法的方式来对调用远程方法做统一的封装处理
        //可以实现通过传入指定的接口的class类返回对应的代理对象
        HelloService helloService = ProxyFactory.getProxy(HelloService.class);
        String result = helloService.hello("zhangtianyu21");
        System.out.println(result);


    }
}
