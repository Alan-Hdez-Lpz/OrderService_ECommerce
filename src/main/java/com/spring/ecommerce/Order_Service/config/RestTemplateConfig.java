package com.spring.ecommerce.Order_Service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;

@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced // enables Eureka-based service discovery
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
