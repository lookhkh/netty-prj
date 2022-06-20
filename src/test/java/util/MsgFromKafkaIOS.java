package util;

import java.util.Arrays;

import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.KafkaKeyEnum;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.MsgType;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.TypeOfSending;

public class MsgFromKafkaIOS {
	public static MsgFromKafkaVo voForMultiplePushWithValidDataBody = TestUtil.createMsgVoForMultipleIOS(DataBodys.bodyOfWithValidHeaderAndBody);
	
	public static MsgFromKafkaVo voForSinglePushWithInvalidHeader =  TestUtil.createMsgVoForSingleIOS(DataBodys.bodyOfInvalidHeaderAndValidBody);
		
	public static MsgFromKafkaVo voForSinglePushWithInvalidBody = TestUtil.createMsgVoForSingleIOS(DataBodys.bodyOfSmsWithValidHeaderAndInvalidBody);
	
	public static MsgFromKafkaVo voForIOSWithInvalidHeaderAndInvalidBody =  TestUtil.createMsgVoForSingleIOS(DataBodys.bodyOfInvalidHeaderAndInValidBody);
			
	public static MsgFromKafkaVo voForSinglePushWithValidDataBody = TestUtil.createMsgVoForSingleIOS(DataBodys.bodyOfWithValidHeaderAndBody);
	
}
