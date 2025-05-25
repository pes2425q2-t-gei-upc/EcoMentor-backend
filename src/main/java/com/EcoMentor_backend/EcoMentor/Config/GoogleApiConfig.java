package com.EcoMentor_backend.EcoMentor.Config; // Or wherever you keep your configurations

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GoogleApiConfig {

    @Bean
    public NetHttpTransport netHttpTransport() throws GeneralSecurityException, IOException {
        return GoogleNetHttpTransport.newTrustedTransport();
    }

    @Bean
    public JsonFactory jsonFactory() {
        return GsonFactory.getDefaultInstance();
    }

    @Bean
    public GoogleIdTokenVerifier.Builder googleIdTokenVerifierBuilder(
            NetHttpTransport httpTransport, JsonFactory jsonFactory, GoogleConfig googleConfig) {
        GoogleIdTokenVerifier.Builder builder = new GoogleIdTokenVerifier.Builder(httpTransport, jsonFactory);

        if (googleConfig != null && googleConfig.getClientId() != null) {
            builder.setAudience(Collections.singletonList(googleConfig.getClientId()));
        }
        return builder;
    }

}