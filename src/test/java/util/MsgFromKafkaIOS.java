package util;

import java.util.Arrays;

import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.KafkaKeyEnum;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.MsgType;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.TypeOfSending;

public class MsgFromKafkaIOS {
	public static MsgFromKafkaVo voForMultiplePushWithValidDataBody = TestUtil.createMsgVoForIOS(DataBodys.bodyOfWithValidHeaderAndBody, TypeOfSending.MULTIPLE, Options.targets);
	
	public static MsgFromKafkaVo voForSinglePushWithInvalidHeader =  TestUtil.createMsgVoForIOS(DataBodys.bodyOfInvalidHeaderAndValidBody, TypeOfSending.SINGLE, Options.target );
		
	public static MsgFromKafkaVo voForSinglePushWithInvalidBody = TestUtil.createMsgVoForIOS(DataBodys.bodyOfSmsWithValidHeaderAndInvalidBody, TypeOfSending.SINGLE, Options.target);
	
	public static MsgFromKafkaVo voForIOSWithInvalidHeaderAndInvalidBody =  TestUtil.createMsgVoForIOS(DataBodys.bodyOfInvalidHeaderAndInValidBody,TypeOfSending.SINGLE, Options.target );
			
	public static MsgFromKafkaVo voForSinglePushWithValidDataBody = TestUtil.createMsgVoForIOS(DataBodys.bodyOfWithValidHeaderAndBody, TypeOfSending.SINGLE, Options.target);
	
}
