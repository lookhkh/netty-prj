package util;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import org.mockito.Mockito;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kt.onnuipay.client.handler.manager.SendManager;
import com.kt.onnuipay.client.handler.manager.SendPushManager;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl.hanlder.PushMultipleManager;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl.hanlder.PushSingleManager;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl.hanlder.SmsMultipleManager;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.manager.impl.hanlder.SmsSingleManager;
import com.kt.onnuipay.kafka.kafkanetty.client.handler.mapper.SmsPushMapper;
import com.kt.onnuipay.kafka.kafkanetty.kafka.DispatcherControllerImpl;
import com.kt.onnuipay.kafka.kafkanetty.kafka.DynamicHandlerManager;
import com.kt.onnuipay.kafka.kafkanetty.kafka.listener.AckMessageListener;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.DataBody;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.ResultOfPush;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.KafkaKeyEnum;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.MsgType;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.TypeOfSending;
import com.kt.onnuipay.kafka.kafkanetty.kafka.mongo.TempMongodbTemplate;
import com.kt.onnuipay.kafka.kafkanetty.kafka.parser.KafkaMsgParser;

public class TestUtil {


	
	
	/***************                 Test VO                   ***********/
	
	public static void resetMockingObj(Object ...obj) {
		for(Object o : obj) {
			o = Mockito.mock(o.getClass());
		}
	}

	public static MsgFromKafkaVo createMsgVo(KafkaKeyEnum key, List<DataBody> databody, MsgType type, TypeOfSending sending, List<String> target) {
		
		return MsgFromKafkaVo.builder()
				.key(key)
				.payload(databody)
				.type(type)
				.kind(sending)
				.actionUrl(Options.actionUrl)
				.timeOfDelievery(Options.time)
				.target(target)
				.sender(Options.sender)
				.build();
	}
	
	public static MsgFromKafkaVo createMsgVoForSingleAndroid(
			List<DataBody> dataBody) {

		return createMsgVo(KafkaKeyEnum.ANDROID, dataBody, MsgType.APP_PUSH, TypeOfSending.SINGLE ,Options.target);
	}
	
	
	public static MsgFromKafkaVo createMsgVoForMultiAndroid(
			List<DataBody> dataBody) {

		return createMsgVo(KafkaKeyEnum.ANDROID, dataBody, MsgType.APP_PUSH, TypeOfSending.MULTIPLE, Options.targets);
	}
	
	
	
	public static MsgFromKafkaVo createMsgVoForSingleIOS(
			List<DataBody> dataBody) {

		return createMsgVo(KafkaKeyEnum.IOS, dataBody, MsgType.APP_PUSH, TypeOfSending.SINGLE,Options.target);
	}
	
	public static MsgFromKafkaVo createMsgVoForMultipleIOS(
			List<DataBody> dataBody) {

		return createMsgVo(KafkaKeyEnum.IOS, dataBody, MsgType.APP_PUSH,TypeOfSending.MULTIPLE ,Options.targets);
	}
	
	
	public static MsgFromKafkaVo createMsgVoForSingleSMS(
			List<DataBody> dataBody) {

		return createMsgVo(KafkaKeyEnum.SMS, dataBody, MsgType.SMS, TypeOfSending.SINGLE, Options.target);
	}
	

	public static MsgFromKafkaVo createMsgVoForMultiSMS(
			List<DataBody> dataBody) {

		return createMsgVo(KafkaKeyEnum.SMS, dataBody, MsgType.SMS, TypeOfSending.MULTIPLE, Options.targets);
	}
	
	
	


	/***************                 Test VO                   ***********/

	/***************                 Test Mock                   ***********/
	public static FirebaseMessaging instance = Mockito.mock(FirebaseMessaging.class);
	
	public static SmsPushMapper mapper = Mockito.mock(SmsPushMapper.class);
	
	/***************                 Test Mock                   ***********/

	
	
	
	/***************                 Test Spy                   ***********/

	public static SendPushManager managerSpy = Mockito.spy(SendPushManager.class);
	
	public static SendManager smsSingle = new SmsSingleManager();
	public static SendManager smsMulti =new SmsMultipleManager();
	public static SendManager pushSingle = new PushSingleManager(instance, managerSpy);
	public static SendManager pushMulti = new PushMultipleManager(instance, managerSpy);
	public static TempMongodbTemplate mongo = Mockito.spy(TempMongodbTemplate.class);

	/***************                 Test Spy                   ***********/

	
	public static DynamicHandlerManager mockingDynamicHanlder = Mockito.mock(DynamicHandlerManager.class);
	public static KafkaMsgParser mockingParser = Mockito.mock(KafkaMsgParser.class);
	public static ExecutorService serviceMock = Mockito.mock(ExecutorService.class);
	public static 	TempMongodbTemplate mockingMongo = TestUtil.mongo;
	public static AckMessageListener listener = new AckMessageListener(new DispatcherControllerImpl(mockingParser, mockingDynamicHanlder, mockingMongo),serviceMock);


	
	public static List<String> getDatas(){
		ObjectMapper ob = new ObjectMapper();

		List<MsgFromKafkaVo> vo = Arrays.asList(
				MsgFromKafkaAndroid.voForAndroidWithInvalidHeaderAndInvalidBody,
				MsgFromKafkaAndroid.voForMultiplePushWithValidDataBody,
				MsgFromKafkaAndroid.voForSinglePushWithInvalidHeader,
				MsgFromKafkaAndroid.voForSinglePushWithValidDataBody,
				MsgFromKafkaIOS.voForIOSWithInvalidHeaderAndInvalidBody,
				MsgFromKafkaIOS.voForMultiplePushWithValidDataBody,
				MsgFromKafkaIOS.voForSinglePushWithInvalidBody,
				MsgFromKafkaIOS.voForSinglePushWithInvalidHeader,
				MsgFromKafkaIOS.voForSinglePushWithValidDataBody,
				MsgFromKafkaSmss.voForMultipleSMSWithInValidDataBodyWithInvalidBody,
				MsgFromKafkaSmss.voForMultipleSMSWithInValidDataBodyWithInvalidHeader,
				MsgFromKafkaSmss.voForMultipleSMSWithValidDataBody,
				MsgFromKafkaSmss.voForSingleSMSWithInValidDataBodyIwthInValidHeader,
				MsgFromKafkaSmss.voForSingleSMSWithInvalidHeaderAndInvalidBody,
				MsgFromKafkaSmss.voForSingleSmsWithValidDataBody
				);
		
		return vo.stream().map(item -> {
			try {
				String data =  ob.writeValueAsString(vo);
				return data;
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}).filter(a->a!=null).collect(Collectors.toList());
		
	}

	public static ResultOfPush createResultOfPushGivenVo(MsgFromKafkaVo vo, boolean result, Throwable t) {
		// TODO Auto-generated method stub
		return ResultOfPush.builder()
					.id(vo.getSender())
					.reason(t)
					.success(result)
					.vo(vo)
					.build();
	}
	
	public static ResultOfPush createSuccessResultOfPushGivenVo(MsgFromKafkaVo vo, boolean result) {
		// TODO Auto-generated method stub
		return createResultOfPushGivenVo(vo,true,null);
	}
	
	public static ResultOfPush createFailResultOfPushGivenVo(MsgFromKafkaVo vo, boolean result, Throwable t) {
		// TODO Auto-generated method stub
		return createResultOfPushGivenVo(vo,false,t);
	}


}
