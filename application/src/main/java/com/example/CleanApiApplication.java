package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = { "com.example" })
@ComponentScan(basePackages = {
        "com.example.inventory",
        "com.example.restapi.config",
        "com.example.auth",
        "com.cexample.gateway",
        "com.example"
})

@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableTransactionManagement
@EntityScan(basePackages = { "com.example" })
@EnableJpaRepositories(basePackages = { "com.example" })
// @EnableCaching
@PropertySource("classpath:application.yml")
// @PropertySource("classpath:application-${spring.profiles.active}.yml")
public class CleanApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(CleanApiApplication.class, args);
    }
}
