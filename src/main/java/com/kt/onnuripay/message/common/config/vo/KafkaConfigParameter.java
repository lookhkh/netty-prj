package com.kt.onnuripay.message.common.config.vo;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@ToString(exclude = {"env"})
@Getter
@Slf4j
@Configuration
public class KafkaConfigParameter {
    
    
    /**
     * 
     * kafka.consumer.container.single.pollInterval = single polling interval
     * kafka.consumer.container.batch.pollInterval = batch polling interval
     * kafka.consumer.container.batch.minByte = Fetch 시 최소 byte 수
     * kafka.consumer.container.batch.maxWaitMs = Fetch 시 대기하는 최대 MS
     * kafka.consumer.container.brokers = 서버 브로커 정보 IP:PORT 형식
     * kafka.consumer.container.maxPollRecords = fetch 시 최대 records 수
     */
    private final Environment env;

    private final int singlePollInterval;
    private final int batchPollInterval;
    
    private final int batchMinBytesConfig;
    private final int batchMaxWaitMsConfig;
    
    private final String brokers;
    private final int maxPollRecords;
   
    public KafkaConfigParameter(Environment env) {
        this.env = env;
        
        String singlePollInterval = env.getProperty("kafka.consumer.container.single.pollInterval");
        String batchPollInterval = env.getProperty("kafka.consumer.container.batch.pollInterval");
        String batchMinBytesConfig = env.getProperty("kafka.consumer.container.batch.minByte");
        String batchMaxWaitMsConfig = env.getProperty("kafka.consumer.container.batch.maxWaitMs");
        String brokers = env.getProperty("kafka.consumer.container.brokers") == null? "localhost:9092" : env.getProperty("kafka.consumer.container.brokers");
        String maxPollRecords = env.getProperty("kafka.consumer.container.maxPollRecords");
        
        this.singlePollInterval = singlePollInterval == null? 1000 : Integer.valueOf(singlePollInterval);
        this.batchPollInterval = batchPollInterval == null? 1000 : Integer.valueOf(batchPollInterval);
        this.batchMinBytesConfig = batchMinBytesConfig == null? 10000 : Integer.valueOf(batchMinBytesConfig);
        this.batchMaxWaitMsConfig = batchMaxWaitMsConfig == null? 1000 : Integer.valueOf(batchMaxWaitMsConfig);
        this.brokers = brokers;
        this.maxPollRecords = maxPollRecords == null? 100 : Integer.valueOf(maxPollRecords);

        log.debug("{} init => result : {}",this.getClass().getName(),this.toString());
    }
    
    
    
    
}
