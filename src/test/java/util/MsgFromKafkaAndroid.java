package util;

import java.util.Arrays;
import java.util.List;

import com.kt.onnuipay.kafka.kafkanetty.kafka.model.DataBody;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.KafkaKeyEnum;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.MsgType;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.TypeOfSending;

public class MsgFromKafkaAndroid {

	public static MsgFromKafkaVo voForMultiplePushWithValidDataBody = TestUtil.createMsgVoForMultiAndroid(DataBodys.bodyOfWithValidHeaderAndBody);
	
	public static MsgFromKafkaVo voForSinglePushWithInvalidHeader =  TestUtil.createMsgVoForSingleAndroid(DataBodys.bodyOfInvalidHeaderAndValidBody);
	
	public static MsgFromKafkaVo voForAndroidWithInvalidHeaderAndInvalidBody = TestUtil.createMsgVoForSingleAndroid(DataBodys.bodyOfInvalidHeaderAndInValidBody) ;
	
	public static MsgFromKafkaVo voForSinglePushWithValidDataBody = TestUtil.createMsgVoForSingleAndroid( DataBodys.bodyOfWithValidHeaderAndBody);
}
//strong