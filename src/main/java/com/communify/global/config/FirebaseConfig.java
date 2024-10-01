package com.communify.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.account.key.path}")
    private String keyPath;

    @Bean
    public FirebaseMessaging firebaseMessaging(final ResourceLoader resourceLoader) throws IOException {
        final Resource resource = resourceLoader.getResource(keyPath);
        final InputStream serviceAccount = resource.getInputStream();

        final FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        return FirebaseMessaging.getInstance(FirebaseApp.initializeApp(firebaseOptions));
    }
}
