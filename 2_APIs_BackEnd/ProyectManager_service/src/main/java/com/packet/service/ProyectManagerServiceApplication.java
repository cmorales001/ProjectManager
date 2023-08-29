package com.packet.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ProyectManagerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProyectManagerServiceApplication.class, args);
    }

    
    //clase de spring boot para hacer peticiones http
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
