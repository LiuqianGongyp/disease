package com.cyuu;

import org.springframework.boot.SpringApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
/**
 * Hello world!
 *
 */
@SpringBootApplication
@RestController
@MapperScan("com.cyuu.dao")
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);

    }
}
