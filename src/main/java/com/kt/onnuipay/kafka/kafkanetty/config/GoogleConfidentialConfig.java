package com.kt.onnuipay.kafka.kafkanetty.config;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;

import lombok.extern.slf4j.Slf4j;

@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@Configuration
public class GoogleConfidentialConfig {

	@Autowired
	Environment env;
	
	   @Bean
	   public GoogleCredentials getGoogleCredentials() throws IOException {
		   String fireBaseCreateScoped = "https://www.googleapis.com/auth/firebase.messaging";
			String credentialPath = "onnuri-4b38d-firebase-adminsdk-jympu-ea1fc6a964.json";

			try {
				GoogleCredentials credential =  GoogleCredentials
										.fromStream(new ClassPathResource(credentialPath).getInputStream())
										.createScoped((Arrays.asList(fireBaseCreateScoped)))
										;			
				
				return credential;
			}catch(IOException e) {
				log.error("googleCredential init failed {}",e.getMessage(),e);
				throw e;
			}
	   }
}
