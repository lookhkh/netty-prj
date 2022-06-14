package util;

import java.util.Arrays;
import java.util.List;

import org.mockito.Mockito;

import com.google.firebase.messaging.FirebaseMessaging;
import com.kafka.kafkanetty.client.handler.manager.SendManager;
import com.kafka.kafkanetty.client.handler.manager.SendPushManager;
import com.kafka.kafkanetty.client.handler.manager.ValidationManager;
import com.kafka.kafkanetty.client.handler.manager.impl.hanlder.PushMultipleManager;
import com.kafka.kafkanetty.client.handler.manager.impl.hanlder.PushSingleManager;
import com.kafka.kafkanetty.client.handler.manager.impl.hanlder.SmsMultipleManager;
import com.kafka.kafkanetty.client.handler.manager.impl.hanlder.SmsSingleManager;
import com.kafka.kafkanetty.client.handler.manager.vo.UserInfoOnPush;
import com.kafka.kafkanetty.client.handler.mapper.SmsPushMapper;
import com.kafka.kafkanetty.kafka.DispatcherControllerImpl;
import com.kafka.kafkanetty.kafka.DynamicHandlerManager;
import com.kafka.kafkanetty.kafka.listener.AckMessageListener;
import com.kafka.kafkanetty.kafka.model.DataBody;
import com.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kafka.kafkanetty.kafka.model.enums.KafkaKeyEnum;
import com.kafka.kafkanetty.kafka.model.enums.MsgType;
import com.kafka.kafkanetty.kafka.model.enums.TypeOfSending;
import com.kafka.kafkanetty.kafka.model.push.AndroidVo;
import com.kafka.kafkanetty.kafka.model.push.IOSVo;
import com.kafka.kafkanetty.kafka.mongo.TempMongodbTemplate;
import com.kafka.kafkanetty.kafka.parser.KafkaMsgParser;

public class TestUtil {

	public static String actionUrl = "www.action.url";
	public static String time = "2022-06-06 13:22:10";
	
	/***************                 Test VO                   ***********/
	public static List<DataBody> bodyOfMultipleSms = Arrays.asList(DataBody.builder()
			.title("SMS Multiple Test Header")
			.body("SMS Mutilple Test Body Msg")
			.build());
	
	public static List<DataBody> bodyOfSingleSms = Arrays.asList(DataBody.builder()
			.title("SMS Single Test Header")
			.body("SMS Single Test Body Msg")
			.build());
	
	public static  List<DataBody> bodyOfMultiplePush = Arrays.asList(DataBody.builder()
			.title("Push Multiple Test Header")
			.body("Push Mutilple Test Body Msg")
			.build());
	
	public static List<DataBody> bodyOfSinglePush = Arrays.asList(DataBody.builder()
			.title("Push Single Test Header")
			.body("Push Single Test Body Msg")
			.build());
	
	public static MsgFromKafkaVo voForMultipleSMS =  MsgFromKafkaVo.builder()
			.key(KafkaKeyEnum.SMS)
			.payload(bodyOfMultipleSms)
			.type(MsgType.SMS)
			.kind(TypeOfSending.MULTIPLE)
			.actionUrl(actionUrl)
			.timeOfDelievery(time)
			.build();
	
	public static MsgFromKafkaVo voForSingleSMS =  MsgFromKafkaVo.builder()
			.key(KafkaKeyEnum.SMS)
			.payload(bodyOfSingleSms)
			.type(MsgType.SMS)
			.kind(TypeOfSending.SINGLE)
			.actionUrl(actionUrl)
			.timeOfDelievery(time)
			.build();
	
	public static MsgFromKafkaVo voForMultiplePush =  MsgFromKafkaVo.builder()
			.key(KafkaKeyEnum.ANDROID)
			.payload(bodyOfMultiplePush)
			.type(MsgType.APP_PUSH)
			.kind(TypeOfSending.MULTIPLE)
			.actionUrl(actionUrl)
			.timeOfDelievery(time)
			.build();
	
	public static MsgFromKafkaVo voForSinglePush =  MsgFromKafkaVo.builder()
			.key(KafkaKeyEnum.ANDROID)
			.payload(bodyOfSinglePush)
			.type(MsgType.APP_PUSH)
			.kind(TypeOfSending.SINGLE)
			.actionUrl(actionUrl)
			.timeOfDelievery(time)
			.build();
	
	
	public static MsgFromKafkaVo voForAndroid =  MsgFromKafkaVo.builder()
				.key(KafkaKeyEnum.ANDROID)
				.payload(bodyOfSinglePush)
				.type(MsgType.APP_PUSH)
				.kind(TypeOfSending.SINGLE)
				.actionUrl(actionUrl)
				.timeOfDelievery(time)
				.build();
	
	public static MsgFromKafkaVo voForIOS =  MsgFromKafkaVo.builder()
				.key(KafkaKeyEnum.IOS)
				.payload(bodyOfSinglePush)
				.type(MsgType.APP_PUSH)
				.actionUrl(actionUrl)
				.timeOfDelievery(time)
				.build();
	
	public static MsgFromKafkaVo voForSMS =  MsgFromKafkaVo.builder()
				.key(KafkaKeyEnum.SMS)
				.payload(bodyOfMultipleSms)
				.type(MsgType.SMS)
				.kind(TypeOfSending.MULTIPLE)
				.actionUrl(actionUrl)
				.timeOfDelievery(time)
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

	public static ValidationManager validMngSpy = Mockito.spy(ValidationManager.class);
	public static SendPushManager managerSpy = Mockito.spy(SendPushManager.class);
	
	public static SendManager smsSingle = Mockito.spy(new SmsSingleManager());
	public static SendManager smsMulti = Mockito.spy(new SmsMultipleManager());
	public static SendManager pushSingle = Mockito.spy(new PushSingleManager(instance, managerSpy ,validMngSpy));
	public static SendManager pushMulti = Mockito.spy(new PushMultipleManager(instance,validMngSpy));
	public static TempMongodbTemplate mongo = Mockito.spy(TempMongodbTemplate.class);

	/***************                 Test Spy                   ***********/

	
	public static DynamicHandlerManager m = Mockito.mock(DynamicHandlerManager.class);
	public static KafkaMsgParser parser = Mockito.mock(KafkaMsgParser.class);
	public static AckMessageListener listener = new AckMessageListener(new DispatcherControllerImpl(parser, m));

}
