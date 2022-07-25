package com.kt.onnuripay.message.common.config.vo;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@ToString(exclude = {"env"})
@Configuration
@Slf4j
public class FcmConfigParameter {

    
    /**
     * fcm.eventloop.thread.number = FCM 이벤트 루프 개수 설정 값.
     * fcm.connection.max = FCM 커넥션 수 설정값. 
     * fcm.connection.pool.idleTime.max = FCM 커넥션 풀 내의 최대 IDLE 허용값.
     * fcm.connection.pool.lifeTime.max = 커넥션 풀 내의 최대 생존 기간
     * fcm.connection.pool.evictTime = 커네션 풀 내의 채널 검사 주기
     * fcm.connection.timeout = fcm 내 커넥션 read / write timeout 시간
     * fcm.connection.pool.maxPendingAcquiireTimeout = 커넥션 풀 내 최대 대기 시간.
     * 
     * 
     * 기본 Seconds 단위
     */
    private final Environment env;
    private final int eventLoops;
    private final int maxConnection;
    private final int maxIdleTime; // 기본 seconds 단위
    private final int maxLifeTime;
    private final int evictTime;
    private final int timeout;
    private final int pendingAcquireTimeout;
    private final String baseUrl;
    
    public FcmConfigParameter(Environment env) {
        
        String eventLoops = env.getProperty("fcm.eventloop.thread.number");
        String maxConnection = env.getProperty("fcm.connection.max");
        String maxIdleTime = env.getProperty("fcm.connection.pool.idleTime.max");
        String maxLifeTime = env.getProperty("fcm.connection.pool.lifeTime.max");
        String evictTime = env.getProperty("fcm.connection.pool.evictTime");
        String timeout = env.getProperty("fcm.connection.timeout");
        String pendingAcquireTimeout = env.getProperty("fcm.connection.pool.maxPendingAcquiireTimeout");
        
        this.env = env;
        this.eventLoops = eventLoops == null? 1 : Integer.valueOf(eventLoops);
        this.maxConnection = maxConnection == null? 200 : Integer.valueOf(maxConnection);
        this.maxIdleTime = maxIdleTime == null? 5 : Integer.valueOf(maxIdleTime);
        this.maxLifeTime = maxLifeTime == null? 5 : Integer.valueOf(maxLifeTime);
        this.evictTime = evictTime == null? 120 : Integer.valueOf(evictTime);
        this.timeout = timeout == null? 60 : Integer.valueOf(timeout);
        this.pendingAcquireTimeout = pendingAcquireTimeout == null? 60 : Integer.valueOf(pendingAcquireTimeout);

        this.baseUrl  =  env.getProperty("project.properties.fcm.fcmUrl");
        
        log.debug("FcmConfigParameter init {}",this.toString());

    }

    
    
}
