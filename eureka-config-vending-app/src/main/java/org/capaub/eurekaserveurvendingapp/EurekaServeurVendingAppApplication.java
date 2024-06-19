package org.capaub.eurekaserveurvendingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaServeurVendingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServeurVendingAppApplication.class, args);
    }

}
