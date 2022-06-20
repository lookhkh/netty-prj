package util;

import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.KafkaKeyEnum;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.MsgType;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.TypeOfSending;

public class MsgFromKafkaSmss {

	public static MsgFromKafkaVo voForMultipleSMSWithValidDataBody = TestUtil.createMsgVoForMultiSMS(DataBodys.bodyOfWithValidHeaderAndBody);
	
	public static MsgFromKafkaVo voForMultipleSMSWithInValidDataBodyWithInvalidHeader =  TestUtil.createMsgVoForMultiSMS(DataBodys.bodyOfInvalidHeaderAndValidBody); 
	
	public static MsgFromKafkaVo voForMultipleSMSWithInValidDataBodyWithInvalidBody = TestUtil.createMsgVoForMultiSMS(DataBodys.bodyOfSmsWithValidHeaderAndInvalidBody);
	
	public static MsgFromKafkaVo voForSingleSMSWithInvalidHeaderAndInvalidBody =  TestUtil.createMsgVoForSingleSMS(DataBodys.bodyOfInvalidHeaderAndInValidBody);
	
	public static MsgFromKafkaVo voForSingleSMSWithInValidDataBodyIwthInValidHeader = TestUtil.createMsgVoForSingleSMS(DataBodys.bodyOfInvalidHeaderAndInValidBody);

	public static MsgFromKafkaVo voForSingleSmsWithValidDataBody = TestUtil.createMsgVoForSingleSMS(DataBodys.bodyOfWithValidHeaderAndBody);
}
