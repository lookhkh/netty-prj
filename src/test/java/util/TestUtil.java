package util;

import org.mockito.Mockito;

import com.google.firebase.messaging.FirebaseMessaging;
import com.kafka.kafkanetty.client.handler.manager.SendManager;
import com.kafka.kafkanetty.client.handler.manager.SendPushManager;
import com.kafka.kafkanetty.client.handler.manager.impl.PushMultipleManager;
import com.kafka.kafkanetty.client.handler.manager.impl.PushSingleManager;
import com.kafka.kafkanetty.client.handler.manager.impl.SmsMultipleManager;
import com.kafka.kafkanetty.client.handler.manager.impl.SmsSingleManager;
import com.kafka.kafkanetty.client.handler.manager.vo.UserInfoOnPush;
import com.kafka.kafkanetty.client.handler.mapper.SmsPushMapper;
import com.kafka.kafkanetty.kafka.DispatcherControllerImpl;
import com.kafka.kafkanetty.kafka.DynamicHandlerManager;
import com.kafka.kafkanetty.kafka.listener.AckMessageListener;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kafka.kafkanetty.kafka.model.enums.KafkaKeyEnum;
import com.kafka.kafkanetty.kafka.model.enums.TypeOfSending;
import com.kafka.kafkanetty.kafka.model.push.AndroidVo;
import com.kafka.kafkanetty.kafka.model.push.IOSVo;
import com.kafka.kafkanetty.kafka.mongo.TempMongodbTemplate;
import com.kafka.kafkanetty.kafka.parser.KafkaMsgParser;

public class TestUtil {

	/***************                 Test VO                   ***********/
	
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
	
	public static UserInfoOnPush userInfoOnPushWithYes = new UserInfoOnPush(true);
	
	public static UserInfoOnPush userInfoOnPushWithNo = new UserInfoOnPush(false);
	
	public static AndroidVo androidVo = Mockito.mock(AndroidVo.class);
	public static IOSVo iosVo = Mockito.mock(IOSVo.class);


	/***************                 Test VO                   ***********/

	/***************                 Test Mock                   ***********/
	public static FirebaseMessaging instance = Mockito.mock(FirebaseMessaging.class);
	
	public static SmsPushMapper mapper = Mockito.mock(SmsPushMapper.class);
	
	/***************                 Test Mock                   ***********/

	
	
	
	/***************                 Test Spy                   ***********/

	public static SendPushManager manager = Mockito.spy(SendPushManager.class);
	
	public static SendManager smsSingle = Mockito.spy(new SmsSingleManager());
	public static SendManager smsMulti = Mockito.spy(new SmsMultipleManager());
	public static SendManager pushSingle = Mockito.spy(new PushSingleManager(instance,mapper,manager));
	public static SendManager pushMulti = Mockito.spy(new PushMultipleManager(instance));
	public static TempMongodbTemplate mongo = Mockito.spy(TempMongodbTemplate.class);

	/***************                 Test Spy                   ***********/

	
	public static DynamicHandlerManager m = Mockito.mock(DynamicHandlerManager.class);
	public static KafkaMsgParser parser = Mockito.mock(KafkaMsgParser.class);
	public static AckMessageListener listener = new AckMessageListener(new DispatcherControllerImpl(parser, m));

}
