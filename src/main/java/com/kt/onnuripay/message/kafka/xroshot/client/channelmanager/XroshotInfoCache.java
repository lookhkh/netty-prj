package com.kt.onnuripay.message.kafka.xroshot.client.channelmanager;

import java.time.LocalTime;
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
import java.time.temporal.ChronoUnit;

import com.kt.onnuripay.message.util.LoggerUtils;

import lombok.extern.slf4j.Slf4j;




/**
 * @author cho hyun il lookhkh37@gmail.com
 * @date 2022. 7. 29.
 * @apiNote Xroshot 관련 정보를 캐싱한다.
 * @apiNote Thread Safe하기 위하여 ThreadLocal을 사용
 * TODO 사용 패턴을 사용하여 ThreadSafe 정책 변경 필요할 것으로 보임 2200729 조현일 
 */
@Slf4j
public class XroshotInfoCache<T> {

    private final ThreadLocal<LocalTime> lastRequestTime;
    private final ThreadLocal<T> json;
    private final long interval;
    
    
    /**
     * 
     * @param interval 밀리세컨드 단위
     */
    public XroshotInfoCache(long interval) {
        this.lastRequestTime = new ThreadLocal<LocalTime>();
        this.json = new ThreadLocal<T>();
        this.interval = interval;
    }

    /**
     * 
     * @return 마지막 요청으로부터 interval로 설장한 초가 경과하지 않았을 경우 기존에 cache 된 정보를 리턴.
     * TODO 캐싱 evict 정책 현재는 30초로 임의로 정해두었는데, 사영 패턴에 따라 변경 피룡 0729 조현일
     */
    public boolean isAvailable() {
        
        LocalTime lastAccessed = lastRequestTime.get();
        LocalTime now = LocalTime.now();
        
        
        long dif = ChronoUnit.MILLIS.between(lastAccessed==null?now:lastAccessed, now);
        
        boolean result = json.get()!=null 
                && lastRequestTime.get()!=null
                && dif<interval;
        
        LoggerUtils.logDebug(log, "XroshotServer Cache hit result [{}] / 차이 : [{}] / interval : [{}]", result,dif,interval);
        
        return result;
        
    }

    /**
     * 
     * @return Stale한 값을 반환할 수 있기 때문에 항상 isAvailable() 메소드를 통해 방어로직을 작성
     */
    public T getCachedData() {
        return json.get();
    }
    
    public T put(T cacheString) {
        
        LocalTime time = LocalTime.now();
        
        this.json.set(cacheString);
        this.lastRequestTime.set(time);
        
        LoggerUtils.logDebug(log, "XroshotCache put new Cached Data json [{}] / time [{}]", cacheString, time);

        
        return cacheString;
    }

}
