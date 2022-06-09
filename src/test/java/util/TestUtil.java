package util;

import org.mockito.Mockito;

import com.google.firebase.messaging.FirebaseMessaging;
import com.kafka.kafkanetty.client.handler.mapper.SmsPushMapper;
import com.kafka.kafkanetty.kafka.model.KafkaKeyEnum;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kafka.kafkanetty.kafka.model.TypeOfSending;

public class TestUtil {

	public static MsgFromKafkaVo voForMultipleSMS =  MsgFromKafkaVo.builder()
			.key(KafkaKeyEnum.SMS)
			.payload("TEST For sms")
			.type(TypeOfSending.MULTIPLE)
			.build();
	
	public static MsgFromKafkaVo voForSingleSMS =  MsgFromKafkaVo.builder()
			.key(KafkaKeyEnum.SMS)
			.payload("TEST For sms")
			.type(TypeOfSending.SINGLE)
			.build();
	
	public static MsgFromKafkaVo voForMultiplePush =  MsgFromKafkaVo.builder()
			.key(KafkaKeyEnum.ANDROID)
			.payload("TEST For sms")
			.type(TypeOfSending.MULTIPLE)
			.build();
	
	public static MsgFromKafkaVo voForSinglePush =  MsgFromKafkaVo.builder()
			.key(KafkaKeyEnum.ANDROID)
			.payload("TEST For sms")
			.type(TypeOfSending.SINGLE)
			.build();
	
	
	public static MsgFromKafkaVo voForAndroid =  MsgFromKafkaVo.builder()
				.key(KafkaKeyEnum.ANDROID)
				.payload("TEST For Android")
				.type(TypeOfSending.SINGLE)
				.build();
	
	public static MsgFromKafkaVo voForIOS =  MsgFromKafkaVo.builder()
				.key(KafkaKeyEnum.IOS)
				.payload("TEST For IOS")
				.type(TypeOfSending.SINGLE)
				.build();
	
	public static MsgFromKafkaVo voForSMS =  MsgFromKafkaVo.builder()
				.key(KafkaKeyEnum.SMS)
				.payload("TEST For sms")
				.type(TypeOfSending.MULTIPLE)
				.build();
	
	public static FirebaseMessaging instance = Mockito.mock(FirebaseMessaging.class);
	
	public static SmsPushMapper mapper = Mockito.mock(SmsPushMapper.class);


}
