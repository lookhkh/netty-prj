package com.kafka.kafkanetty.client.test.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Response;
import org.junit.jupiter.api.Test;

import com.google.auth.oauth2.GoogleCredentials;

public class LibTest {


	@Test
	public void t() throws IOException, InterruptedException {
		String value = getAccessToken();
		System.out.println(value);
		
		AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient();
		for(int i=0; i<5; i++) {
			ListenableFuture<Response> r = asyncHttpClient
				.prepareGet("https://jsonplaceholder.typicode.com/todos/"+i)
				.addHeader("Authorization", "Bearer "+this.getAccessToken())
				.execute();
			r.addListener(extracted(r), null);

		}
		Thread.sleep(100000);
		
		asyncHttpClient.close();
		
	}

	private Runnable extracted(ListenableFuture<Response> f) {
		return ()->{
			try {
				System.out.println(f.get().getResponseBody());
				System.out.println("++++++++++++++++++ "+Thread.currentThread().getName());
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
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
