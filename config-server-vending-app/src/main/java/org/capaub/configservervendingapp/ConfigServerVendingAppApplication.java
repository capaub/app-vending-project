package org.capaub.configservervendingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ConfigServerVendingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerVendingAppApplication.class, args);
    }

}
