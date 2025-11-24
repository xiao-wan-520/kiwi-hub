package com.iot.kiwiuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class KiwiUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(KiwiUserApplication.class, args);
    }
}
