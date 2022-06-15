package com.kt.onnuipay.kafka.kafkanetty.config;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


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

@AllArgsConstructor
@Configuration
@PropertySource("classpath:application.properties")
@Slf4j
public class FireBaseConfig {

	   @Autowired
	   Environment env;
	   
	   
	   /**
	    * TODO Firebase instance가 초기화를 실패할 경우 대처법 생각필요 20220609 조현일
	    * @apiNote FCM 초기화 객체 생성
	    * 
	    * 
	    * **/
	   @Bean
	   public FirebaseMessaging firebaseSetting() throws URISyntaxException {
			
			String fireBaseCreateScoped =  env.getProperty("project.properties.fms.fireBaseCreateScoped");
			String credentialPath = "onnuri-4b38d-firebase-adminsdk-jympu-ea1fc6a964.json";


			GoogleCredentials googleCredentials;
			try {
				googleCredentials = GoogleCredentials
										.fromStream(new ClassPathResource(credentialPath).getInputStream())
										.createScoped((Arrays.asList(fireBaseCreateScoped)));			

		        FirebaseOptions secondaryAppConfig = FirebaseOptions.builder()
		                .setCredentials(googleCredentials)
		                .build();
		        FirebaseApp app = FirebaseApp.initializeApp(secondaryAppConfig);
		        FirebaseMessaging instance = FirebaseMessaging.getInstance(app);
		        
		        log.info("FireBaseMsgSDK INIT FINISHED, {},{}",instance.toString());
		        
		        return instance;
		        
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				log.error("FireBaseMessaging Instance failed to init",e);
				return null;
			}

		}
	   
}
