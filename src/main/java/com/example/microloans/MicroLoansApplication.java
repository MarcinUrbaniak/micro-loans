package com.example.microloans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource(value = "classpath:message.properties", encoding = "UTF-8")
@SpringBootApplication
public class MicroLoansApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroLoansApplication.class, args);
    }

}
