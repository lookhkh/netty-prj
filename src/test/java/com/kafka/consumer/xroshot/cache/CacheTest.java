package com.kafka.consumer.xroshot.cache;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kt.onnuripay.message.kafka.xroshot.client.channelmanager.XroshotInfoCache;

/**
 * @author cho hyun il lookhkh37@gmail.com
 * @date 2022. 7. 29.
 * @implNote <p> 멀티쓰레드 상황에서도 주어진 조건(마지막 요청 시간이 현재시간과의 차이가 주어진 interval 이내에 들어온다)을 만족하면 cached 된 데이터를 반환하며, 그러지 않은 경우, 새로 캐시를 설정한다. </p>
 *
 */
public class CacheTest {

    @Test
    @DisplayName("싱글 스레드 상황에서, 첫 번째 요청 이후, 두 번째 요청까지 지난 시간이 interval 이내일 경우, cache 된 데이터를 반환한다")
    public void t() throws InterruptedException {
        int interval = 100;
        
        run(interval);

    }

   
    
    @Test
    @DisplayName("멀티 스레드 상황에서,첫 번째 요청 이후, 두 번째 요청까지 지난 시간이 interval 이내일 경우, cache 된 데이터를 반환한다 ")
    public void t2() throws InterruptedException {
        
        List<Thread> t = new ArrayList<>();
        for(int i=0; i<3; i++) {
            t.add(new Thread(()->{
                try {
                    run(50);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }));
        }
        
        t.forEach(th->th.start());
        Thread.sleep(1000);
    }
    
    private void run(int interval) throws InterruptedException {
        
        XroshotInfoCache<String> cache = new XroshotInfoCache<>(interval);
        
        String inputData = "first Data";

        cache.put(inputData);

        assertTrue(cache.isAvailable());
        assertTrue(cache.getCachedData().equals(inputData));
        Thread.sleep(interval);
        
        assertFalse(cache.isAvailable());
    }
}
