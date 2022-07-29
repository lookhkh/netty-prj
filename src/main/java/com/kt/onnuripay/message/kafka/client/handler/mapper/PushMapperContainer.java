package com.kt.onnuripay.message.kafka.client.handler.mapper;

import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.kt.onnuripay.message.kafka.model.ResultOfPush;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class PushMapperContainer {

    private final SmsPushMapper mapper;
    private final ExecutorService pool;
    
    public PushMapperContainer(SmsPushMapper mapper, @Qualifier("db-thread-pool") ExecutorService pool) {
        this.mapper = mapper;
        this.pool = pool;
    }
    
    public void insetResultOfPush(ResultOfPush result) {
       pool.execute(()-> mapper.insertDbHistory(result));
    }
    
    
    
    
    
}
