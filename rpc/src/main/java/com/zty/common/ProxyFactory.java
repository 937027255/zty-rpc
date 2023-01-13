package com.zty.common;

import com.zty.protocol.HttpClient;
import com.zty.register.RemoteRegister;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.List;

public class ProxyFactory {
    public static <T> T getProxy(Class interfactClass) {
        return (T) Proxy.newProxyInstance(ProxyFactory.class.getClassLoader(), new Class[]{interfactClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Invocation invocation = new Invocation(interfactClass.getName(), method.getName(),
                        method.getParameterTypes(), args);
                HttpClient httpClient = new HttpClient();
                //发现服务
                List<URL> urls = RemoteRegister.get(interfactClass.getName());

                List<URL> usedUrls = new ArrayList<>();
                //服务调用
                URL url = null;
                String result = null;
                int max = 3;
                while (max > 0) {
                    try {
                        //负载均衡
                        urls.removeAll(usedUrls);
                        url = LoadBalance.getRandomUrl(urls);
                        max--;

                        result = httpClient.send(url.getHost(), url.getPort(), invocation);
                    } catch (Exception e) {
                        usedUrls.add(url);
                        if (max != 0 && urls.size() > 0) continue;
                        return "ERROE!!!";
                    }
                }
                return result;
            }
        });
    }
}
