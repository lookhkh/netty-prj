/*
 * KT OnnuriPay version 1.0
 *
 *  Copyright ⓒ 2022 kt corp. All rights reserved.
 *
 *  This is a proprietary software of kt corp, and you may not use this file except in
 *  compliance with license agreement with kt corp. Any redistribution or use of this
 *  software, with or without modification shall be strictly prohibited without prior written
 *  approval of kt corp, and the copyright notice above does not evidence any actual or
 *  intended publication of such software.
 */
package com.kt.onnuripay.message.common.config.unit.fcm;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

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
		   String fireBaseCreateScoped = env.getProperty("project.properties.fcm.fireBaseCreateScoped");
			String credentialPath = env.getProperty("project.properties.fcm.credentialPath");
			
			
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
