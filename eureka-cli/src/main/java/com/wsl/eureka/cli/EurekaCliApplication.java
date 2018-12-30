package com.wsl.eureka.cli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Author wsl
 * Date 2018/12/30
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaCliApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaCliApplication.class, args);
    }
}
