package com.zty.common;

import com.zty.protocol.HttpClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {
    public static <T> T getProxy(Class interfactClass) {
        return (T) Proxy.newProxyInstance(ProxyFactory.class.getClassLoader(), new Class[]{interfactClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Invocation invocation = new Invocation(interfactClass.getName(), method.getName(),
                        method.getParameterTypes(), args);
                HttpClient httpClient = new HttpClient();
                String result = httpClient.send("localhost", 8080, invocation);
                System.out.println(result);
                return result;
            }
        });
    }
}
