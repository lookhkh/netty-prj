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
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.kt.onnuripay.message.kafka.xroshot.client.channelmanager.XroshotChannelManager;

import io.netty.channel.EventLoopGroup;
import lombok.extern.slf4j.Slf4j;


/**
 * @author cho hyun il lookhkh37@gmail.com
 * @date 2022. 7. 21.
 * @implNote ExecutorService와 같은 자원들을 어플리케이션이 shutdown 될 시 관리한다.
 *
 */
@Slf4j
@Component
public class ApplicationResourceShutdownManager {

    private final ExecutorService singleExecutors;
    private final ExecutorService dbPoolExecutors;
    private final EventLoopGroup nettyEventLoop;
    private final ScheduledExecutorService scheduler;
    private final XroshotChannelManager manager;
    
    public ApplicationResourceShutdownManager(
            @Qualifier("single") ExecutorService singleExecutors, 
            @Qualifier("db-thread-pool")ExecutorService dbPoolExecutors,
            @Qualifier("netty-event-group")EventLoopGroup nettyEventLoop,
            XroshotChannelManager manager,
            @Qualifier("scheduler-thread") ScheduledExecutorService scheduler) {
        this.singleExecutors = singleExecutors;
        this.dbPoolExecutors = dbPoolExecutors;
        this.nettyEventLoop = nettyEventLoop;
        this.manager = manager;
        this.scheduler = scheduler;
    }
    
    /**
     * @apiNote App이 shutdown될 시 호출되며, 리소스를 관리한다.
     */
    @PreDestroy
    public void destroyResource() {
       log.info("Container get shutdown signal and start managing resource");
       try {
            singleExecutors.awaitTermination(10, TimeUnit.SECONDS);
            dbPoolExecutors.awaitTermination(10, TimeUnit.SECONDS);
            nettyEventLoop.awaitTermination(10, TimeUnit.SECONDS);
            manager.closeChannel();
            scheduler.awaitTermination(10, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        
    }
   

    }
    
    
}
