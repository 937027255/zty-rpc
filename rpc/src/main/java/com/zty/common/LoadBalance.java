package com.zty.common;

import java.util.List;
import java.util.Random;

public class LoadBalance {
    public static URL getRandomUrl(List<URL> urls) {
        Random random = new Random();
        if (urls.size() == 0) throw new RuntimeException("没有可用的注册服务");
        int i = random.nextInt(urls.size());

        return urls.get(i);
    }
}
