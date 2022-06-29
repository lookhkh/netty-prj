package com.kafka.kafkanetty.client.test.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.database.util.JsonMapper;
import com.google.gson.JsonObject;

import datavo.msg.MessageWrapper;

public class LibTest {

	class Simpe{
		String data;
		int age;
	
	public Simpe() {
		// TODO Auto-generated constructor stub
	}
	
	
	}
	
	@Test
	public void t() throws IOException, InterruptedException {
		
//		AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient();
//		String hi = "hi";
//			 asyncHttpClient
//				.prepareGet("https://jsonplaceholder.typicode.com/todos/1")
//				.addHeader("Authorization", "Bearer ")
//				.execute()
//				.toCompletableFuture()
//				.thenApply(response -> response.getResponseBody())
//				
//				.thenApply(response -> extracted(response))
//				.thenAccept(map -> doRestLogic(map, hi));
//			 	
//		Thread.sleep(100000);
//		
//		asyncHttpClient.close();
//		
	  
		
	XmlMapper mapper = new XmlMapper();
	Simple a = new Simple("hi",1);
	
		
		
		String result = mapper.writeValueAsString(a);
		Simple t = mapper.readValue(result, Simple.class);
		
		System.out.println(result);
		System.out.println(t.toString());

	}

	private Object doRestLogic(Object map, String hi) {

		System.out.println(map+hi);
		
		return null;
	}

	private Object extracted(String res) {
		try {
			return JsonMapper.parseJson(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private static String getAccessToken() throws IOException {
		FileInputStream r =  new FileInputStream("C:\\Users\\PC\\Downloads\\kafka-netty\\kafka-netty\\src\\main\\resources\\onnuri-4b38d-firebase-adminsdk-jympu-ea1fc6a964.json");
		
		
		  GoogleCredentials googleCredentials = GoogleCredentials
		          .fromStream(r)
		          .createScoped(Arrays.asList("https://www.googleapis.com/auth/firebase.messaging"));
	//	  googleCredentials.refreshIfExpired();
		  
		  return googleCredentials.getAccessToken().getTokenValue();
		}
	
}
