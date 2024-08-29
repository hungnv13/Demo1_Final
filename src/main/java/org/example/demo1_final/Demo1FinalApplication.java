package org.example.demo1_final;

import org.example.demo1_final.config.BankConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(BankConfig.class)
public class Demo1FinalApplication {
    public static void main(String[] args) {
        SpringApplication.run(Demo1FinalApplication.class, args);
    }
}
