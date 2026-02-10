package com.chitkara.bfhl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BfhlApplication {

	public static void main(String[] args) {
		SpringApplication.run(BfhlApplication.class, args);

	}
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
