package com.zty.protocol;

import com.zty.common.Invocation;
import com.zty.register.LocalRegister;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HttpServletHandler {
    public void handler(HttpServletRequest req, HttpServletResponse resp){
        try {
            //在request中解析出来invocation对象
            Invocation invocation = (Invocation) new ObjectInputStream(req.getInputStream()).readObject();
            //调用实现类的方法
            String interfaceName = invocation.getInterfaceName();
            Class classImpl = LocalRegister.get(interfaceName);
            Method method = classImpl.getMethod(invocation.getMethodName(), invocation.getParameterTypes());
            String result = (String)method.invoke(classImpl.newInstance(), invocation.getParameters());
            //将调用结果写入response中去
            IOUtils.write(result,resp.getOutputStream());


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
