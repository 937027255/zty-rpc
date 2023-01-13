package com.zty.register;

import com.zty.common.URL;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemoteRegister implements Serializable {
    private static Map<String, List<URL>> map = new HashMap<>();

    public static void regist(String interfaceName,URL url) {
        List<URL> urls = map.get(interfaceName);
        if (urls == null) urls = new ArrayList<URL>();
        urls.add(url);
        map.put(interfaceName,urls);

        saveObject();
    }

    public static List<URL> get(String interfaceName) {
        Map<String, List<URL>> obj = getObject();
        return obj.get(interfaceName);
    }

    public static void saveObject() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("/Users/zhangtianyu/Documents/zty-rpc/remoteMap.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(map);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, List<URL>> getObject() {
        try {
            FileInputStream fileInputStream = new FileInputStream("/Users/zhangtianyu/Documents/zty-rpc/remoteMap.txt");
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            return (Map<String, List<URL>>) ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
