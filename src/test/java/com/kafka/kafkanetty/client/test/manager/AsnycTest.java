package com.kafka.kafkanetty.client.test.manager;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Test;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;

public class AsnycTest {


	
	@Test
	public void test() throws InterruptedException, ExecutionException, TimeoutException {
		
		ListenableFuture<String> f= MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor()).submit(
					new Callable<String>() {
						public String call() throws Exception {
							Thread.currentThread().sleep(1000);
							return "hi";
						}
					}
				);
		


		
		f.addListener(()->{
			if(f.isDone()) {
				try {
					System.out.println(f.get()+" gotten from ");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(f.cancel(false)) {
				
			}
		}, Executors.newFixedThreadPool(100));
		
		
		
		
		Thread.currentThread().sleep(3000);
		
		
		
//		 	getList()
//				 .thenApply(batch->batch.get(3, TimeUnit.SECONDS))
//				 .thenApply(response -> response.getResponses())
//				 .thenApply(list -> list.stream().collect(Collectors.partitioningBy(ele->ele.isSuccessful(), Collectors.toList())));
	}

	private YesOrNo insertDb(YesOrNo success) {
		int ran = new Random().nextInt(5000);
		System.out.println(success.toString()+ Thread.currentThread().getId()+ " this delay "+ran);

		
		return success;
	}
}
