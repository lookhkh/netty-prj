/**
 * 
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
package com.kt.onnuripay.message.common.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import io.netty.channel.EventLoopGroup;
import lombok.extern.slf4j.Slf4j;





/**
 * @author cho hyun il lookhkh37@gmail.com
 * @date 2022. 7. 20.
 * @implNote ThreadExecutors 리소스 관리. container에 exit signal 수신 시, executors의 awaitTermination 호출하며 30초간 유지
 * 
 * 
 *
 */
@Slf4j
@Component
public class ApplicationResourceShutdownHook {

    private final ExecutorService singleExecutors;
    private final ExecutorService dbPoolExecutors;
    private final EventLoopGroup nettyEventLoop;
    
    public ApplicationResourceShutdownHook(@Qualifier("single") ExecutorService singleExecutors, @Qualifier("db-thread-pool")ExecutorService dbPoolExecutors,
            @Qualifier("netty-event-group")EventLoopGroup nettyEventLoop) {
        this.singleExecutors = singleExecutors;
        this.dbPoolExecutors = dbPoolExecutors;
        this.nettyEventLoop = nettyEventLoop;
    }

    @PreDestroy
    public void destroyResource() {
       log.info("Container get shutdown signal and start managing resource");
       try {
            singleExecutors.awaitTermination(10, TimeUnit.SECONDS);
            dbPoolExecutors.awaitTermination(10, TimeUnit.SECONDS);
            nettyEventLoop.awaitTermination(10, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        
    }
   

    }
    
    
    
    
}
