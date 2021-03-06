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
package com.kt.onnuripay.message.common.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kt.onnuripay.message.common.config.vo.ApplicationResourceConfigParameter;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author cho hyun il lookhkh37@gmail.com
 * @date 2022. 7. 15.
 * @implNote ThreadPool Resource Manager Config. 
 * TODO APPLICATION Shutdown 시 리소스가 Leaked 된 것은 없는지 체크 확실히 할 것. 220715 조현일
 */
@AllArgsConstructor
@Data
@Configuration
public class ApplicationResourceConfig {

    private final ApplicationResourceConfigParameter param; 
  
    
    /**
     * TODO 스레드플 개수 지정 220616 조현일
     * - 데이터 처리 로직은 싱글쓰레드가 담당
     * **/
    @Bean("single")
    public ExecutorService getDefault() {
        
        ExecutorService service =  Executors.newSingleThreadExecutor(new ThreadFactory() {
            int cnt = 0;
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("Thread-Wokrer-Push_Server "+cnt);
                cnt++;
                return t;
            }
        });

        return service;
    }
    
    @Bean("scheduler-thread")
    public ScheduledExecutorService getScheduler() {
        ScheduledExecutorService  service =  Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("scheduler-thread-worker");
                return t;
            }
        });

        return service;
    }
    
    /**
     * TODO 스레드플 개수 지정 220616 조현일
     * - DB 커넥션 수에 맞게 동적으로 지정 예정. 
     * **/
    @Bean("db-thread-pool")
    public ExecutorService getDbHandlingThreadPoolDefault() {
        ExecutorService service =  Executors.newFixedThreadPool(param.getDbConnectionThreadNum(),new ThreadFactory() {
            int cnt = 0;
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("Thread-Wokrer-DB "+cnt);
                cnt++;
                return t;
            }
        });
        
        return service;
        
    }
    
    @Bean("fcm-multi-push")
    public ExecutorService getFCMMulitiMessageThreadPool() {
        
        ExecutorService service =  Executors.newFixedThreadPool(param.getFcmMultiPushThreadNum(),new ThreadFactory() {
            int cnt = 0;
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("Thread-Wokrer-FCM-Multipush "+cnt);
                cnt++;
                return t;
            }
        });
        
        return service;
        
    }
    
    /**
     * 
     * TODO Xroshot Netty 사용하는 EventLoop, 개수 지정 최적화 필요 조현일 220701
     * 
     * 
     * **/
    @Bean("netty-event-group")
    public EventLoopGroup getDefaultNioEventLoopGroup() {
        
        int threads = Integer.valueOf(param.getNettyThreadsNum());
        
        return new NioEventLoopGroup(threads);
    }

}
