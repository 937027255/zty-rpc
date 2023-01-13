package com.zty;

import com.zty.common.URL;
import com.zty.protocol.HttpServer;
import com.zty.register.LocalRegister;
import com.zty.register.RemoteRegister;
import com.zty.remote.HelloService;
import com.zty.service.HelloServiceImpl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

public class Provider {
    public static void main(String[] args) {
        //将提供远程调用的方法进行本地注册
        LocalRegister.regist(HelloService.class.getName(), HelloServiceImpl.class);

        //进行远程方法注册
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
            String ip = addr.getHostAddress().toString();
            System.out.println("ip:" + ip);
            RemoteRegister.regist(HelloService.class.getName(),new URL(ip,8080));

            Map<String, List<URL>> object = RemoteRegister.getObject();
            List<URL> urls = object.get(HelloService.class.getName());
            System.out.println(urls.get(0).getHost());

            //启动tomcat
            HttpServer httpServer = new HttpServer();
            httpServer.start(ip,8080);

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }



    }
}
