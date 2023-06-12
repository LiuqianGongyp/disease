package com.cyuu.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
// 类实现ApplicationRunner或CommandLineRunner接口都可以实现预加载
public class MyApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 获取数据库表已存储的各个服务的IP地址数据
        // 进行socket重新连接操作
        System.out.println("=====ApplicationRunner=====");
    }
}
