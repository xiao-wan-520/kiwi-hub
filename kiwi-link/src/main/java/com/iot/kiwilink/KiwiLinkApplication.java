package com.iot.kiwilink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class KiwiLinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(KiwiLinkApplication.class, args);
    }
}
