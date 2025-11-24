package com.iot.kiwigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class KiwiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(KiwiGatewayApplication.class, args);
    }

}
