package com.demo.t_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan({"com.demo"})
public class TWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TWebApplication.class, args);
    }

}
