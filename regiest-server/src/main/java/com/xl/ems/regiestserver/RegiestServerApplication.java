package com.xl.ems.regiestserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class RegiestServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegiestServerApplication.class, args);
    }

    @Autowired
    DiscoveryClient client;
}
