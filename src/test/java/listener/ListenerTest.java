package listener;

import java.util.concurrent.ExecutorService;

import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ContextConfiguration;

import com.kt.onnuripay.message.KafkaNettyApplication;
import com.kt.onnuripay.message.kafka.controller.DispatcherController;
import com.kt.onnuripay.message.kafka.listener.AckMessageListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ContextConfiguration(classes = KafkaNettyApplication.class)
@SpringBootTest(properties = {"spring.profiles.active: test"})
@DisplayName("통합 테스트 용, 현재는 parser 부분이 미개발 중이라 계속 에러 나올 것임 220609 조현일")
@EmbeddedKafka(topics = "hello.kafka",ports = {9092})
public class ListenerTest {

	@Autowired
	DispatcherController cont;
	@Autowired
	@Qualifier("single")
	ExecutorService service;
	
	
	AckMessageListener listener = new AckMessageListener(cont,service);
	
	

}
