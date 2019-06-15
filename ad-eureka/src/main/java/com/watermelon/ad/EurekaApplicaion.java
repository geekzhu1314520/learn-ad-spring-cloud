package com.watermelon.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaApplicaion {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplicaion.class, args);
    }

}
