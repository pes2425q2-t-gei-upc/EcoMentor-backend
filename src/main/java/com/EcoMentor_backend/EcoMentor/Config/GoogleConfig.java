package com.EcoMentor_backend.EcoMentor.Config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "google.oauth")
public class GoogleConfig {
    private String clientId;
}
