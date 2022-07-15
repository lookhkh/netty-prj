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
package com.kt.onnuripay.common.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * @author cho hyun il lookhkh37@gmail.com
 * @date 2022. 7. 15.
 * @implNote ThreadPool Resource Manager Config. 
 * TODO APPLICATION Shutdown 시 리소스가 Leaked 된 것은 없는지 체크 확실히 할 것. 220715 조현일
 */

@Configuration
public class ApplicationResourceConfig {

    @Autowired
    Environment env;
    
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
    
    /**
     * TODO 스레드플 개수 지정 220616 조현일
     * - DB 커넥션 수에 맞게 동적으로 지정 예정. 
     * **/
    @Bean("db-thread-pool")
    public ExecutorService getDbHandlingThreadPoolDefault() {
        ExecutorService service =  Executors.newFixedThreadPool(1,new ThreadFactory() {
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
    
    /**
     * 
     * 
     * 
     * TODO Netty & AsyncClient가 함게 사용하는 EventLoop, 개수 지정 최적화 필요 조현일 220701
     * => AsyncClient 사용 X로 변경 220714 조현일
     * 
     * **/
    @Bean("netty-event-group")
    public EventLoopGroup getDefaultNioEventLoopGroup() {
        
        int threads = Integer.valueOf(env.getProperty("netty.client.loopgroup.threads"));
        
        return new NioEventLoopGroup(threads);
    }
}
