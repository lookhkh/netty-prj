package com.kafka.kafkanetty.client.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kafka.kafkanetty.client.ClientBootStrap;


public class ClientBootStrapTest {
	

	static String host = "localhost";
	static int port = 8085;
	

	@Test
	@DisplayName("주어진 Host와 Port로 커넥션을 맺는다.")
	public void connectTest() throws Exception {	
	

		System.out.println(2);
		ClientBootStrap bootStrap = new ClientBootStrap(host, port);

		String connectedHost = bootStrap.getHost();
		int connectedPort = bootStrap.getPort();
		
		assertEquals(host,connectedHost);
		assertEquals(connectedPort,port);
		

	}
	
//	@Test
//	@DisplayName("Connected 서버에 요청을 보낸다")
//	public void sendTestViaClientBootStrapClass() {
//		ClientBootStrap bootStrap = new ClientBootStrap(host, 8080);
//		bootStrap.start();
//		
//		bootStrap.send(new ClientStringBasedRequestWrapper("MSG"));
//		
//	}
}
