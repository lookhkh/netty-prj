package com.kt.onnuripay.message.common.config.vo;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@ToString(exclude = {"env"})
@Configuration
@Slf4j
public class ApplicationResourceConfigParameter {

    /**
     * 
     * netty.client.loopgroup.threads= netty eventloop thread 수
     * netty.client.pool.dbConnectionNums = DB 입출력을 담당할 스레드 수
     */
    private final Environment env;
    private final int nettyThreadsNum;
    private final int dbConnectionThreadNum;
    
    public ApplicationResourceConfigParameter(Environment env) {
        
        String nettyThreadsNum = env.getProperty("netty.client.loopgroup.threads");
        String dbConnectionNum = env.getProperty("netty.client.pool.dbConnectionNums");
        
        this.env = env;
        this.nettyThreadsNum = nettyThreadsNum==null? 1 : Integer.valueOf(nettyThreadsNum);
        this.dbConnectionThreadNum = dbConnectionNum==null? 1 : Integer.valueOf(dbConnectionNum);
       // this.nettyThreadsNum = nettyThreadsNum;
        
        log.debug("ApplicationResourceConfigParameter init {}",this.toString());
    }
    
    
    
    
}
