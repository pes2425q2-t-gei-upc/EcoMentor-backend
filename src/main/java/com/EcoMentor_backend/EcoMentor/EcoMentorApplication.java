package com.EcoMentor_backend.EcoMentor;

import com.EcoMentor_backend.EcoMentor.Config.GoogleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(GoogleConfig.class)
public class EcoMentorApplication {
    public static void main(String[] args) {
        SpringApplication.run(EcoMentorApplication.class, args);
    }


}
