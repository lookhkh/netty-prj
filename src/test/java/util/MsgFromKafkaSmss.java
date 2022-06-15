package util;

import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.KafkaKeyEnum;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.MsgType;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.TypeOfSending;

public class MsgFromKafkaSmss {

	public static MsgFromKafkaVo voForMultipleSMSWithValidDataBody = TestUtil.createMsgVoForSMS(DataBodys.bodyOfWithValidHeaderAndBody, TypeOfSending.MULTIPLE, Options.targets);
	
	public static MsgFromKafkaVo voForMultipleSMSWithInValidDataBodyWithInvalidHeader =  TestUtil.createMsgVoForSMS(DataBodys.bodyOfInvalidHeaderAndValidBody,TypeOfSending.MULTIPLE, Options.targets ); 
	
	public static MsgFromKafkaVo voForMultipleSMSWithInValidDataBodyWithInvalidBody = TestUtil.createMsgVoForSMS(DataBodys.bodyOfSmsWithValidHeaderAndInvalidBody, TypeOfSending.MULTIPLE, Options.targets);
	
	public static MsgFromKafkaVo voForSingleSMSWithInvalidHeaderAndInvalidBody =  TestUtil.createMsgVoForSMS(DataBodys.bodyOfInvalidHeaderAndInValidBody,TypeOfSending.SINGLE, Options.target );
	
	public static MsgFromKafkaVo voForSingleSMSWithInValidDataBodyIwthInValidHeader = TestUtil.createMsgVoForSMS(DataBodys.bodyOfInvalidHeaderAndInValidBody, TypeOfSending.SINGLE, Options.target);

	public static MsgFromKafkaVo voForSingleSmsWithValidDataBody = TestUtil.createMsgVoForSMS(DataBodys.bodyOfWithValidHeaderAndBody,TypeOfSending.SINGLE, Options.target );
}
