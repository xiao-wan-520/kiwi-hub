package com.iot.kiwicontent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class KiwiContentApplication {

    public static void main(String[] args) {
        SpringApplication.run(KiwiContentApplication.class, args);
    }
}
