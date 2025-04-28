package com.medipath.labcare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.medipath.labcare.repository")
@ComponentScan(basePackages = {"com.medipath.labcare.service", "com.medipath.labcare.controller", "com.medipath.labcare.config"})
public class MediPathLabCareApplication {
	
	

    public static void main(String[] args) {
        SpringApplication.run(MediPathLabCareApplication.class, args);
        System.out.println("MediPath LabCare started!");
    }
}
