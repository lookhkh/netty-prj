package com.kafka.kafkanetty.netty.client.test;

import java.net.URI;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.core.publisher.Mono;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.resources.LoopResources;

public class ChannelPool {

    AtomicInteger i = new AtomicInteger(0);
   
    
    //@Test
    public void a2() {
        Mono<String> mono = Mono.just("hi");
       
        mono
        .error(new RuntimeException("error"))
        .checkpoint("start", true)
        .onErrorContinue((t,obj)->Mono.error(t))
        .subscribe((str)->System.out.println(str), t->System.out.println(t.getMessage()));
        
        
    }
    
   @Test
   public void t() throws InterruptedException {
      
       LoopResources loop = LoopResources.create("worker-event-loop", 1, 10, true);

       
       ConnectionProvider provider =
               ConnectionProvider.builder("custom")
                                 .maxConnections(200)
                                 .pendingAcquireMaxCount(400)
                                 .maxIdleTime(Duration.ofSeconds(20))           
                                 .maxLifeTime(Duration.ofSeconds(60))           
                                 .pendingAcquireTimeout(Duration.ofSeconds(60))
                                 .evictInBackground(Duration.ofSeconds(120))    
                                 .build();
       ReactorResourceFactory factory = new ReactorResourceFactory();
       factory.setLoopResources(loop);
       factory.setConnectionProvider(provider);
       
       String baseUrl = "https://jsonplaceholder.typicode.com";
       DefaultUriBuilderFactory uriFactory = new DefaultUriBuilderFactory(baseUrl);

       
       WebClient client = WebClient.builder()
               .defaultHeader("Any-msg", "hi hi")
               .uriBuilderFactory(uriFactory)
               
               .clientConnector(new ReactorClientHttpConnector(
                       factory,
                       httpClient -> httpClient
                           .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                           .doOnConnected(connection ->
                               connection.addHandlerLast(new ReadTimeoutHandler(5)
                               ).addHandlerLast(new WriteTimeoutHandler(5))
                           ).responseTimeout(Duration.ofSeconds(5)) // 0.9.11 부터 추가
                   ))
               
               .build();
       

     for(int i=1; i<3; i++) {
          
         client.get().uri(builder -> builder.path("/todos/1").build())
                          .acceptCharset(Charset.forName("utf-8"))
                          
                          .header("from", "reactors")
                          .retrieve()
                          .bodyToMono(String.class)


                          .subscribe(str -> {
                              System.out.println(str);
                          }, t->System.out.println(t.getMessage()));
          
         }
//     long start =System.currentTimeMillis();
//     while(this.i.get()!=99) {
//         
//     }
      
     
     Thread.sleep(3000);
     
     provider.maxConnectionsPerHost().forEach((arr,num)->System.out.println(arr+"   =>   "+num));

      
      
      Thread.sleep(10_000);
      
      
   }

}
