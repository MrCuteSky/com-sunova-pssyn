package com.sunova.psinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PsInfoApplication {
    public static void main(String[] args) {
        System.setProperty("com.sun.jndi.ldap.object.disableEndpointIdentification","true");
        SpringApplication.run(PsInfoApplication.class, args);
    }
}