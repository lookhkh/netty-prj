package util;

import java.util.Arrays;
import java.util.List;

import com.kt.onnuipay.kafka.kafkanetty.kafka.model.DataBody;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.MsgFromKafkaVo;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.KafkaKeyEnum;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.MsgType;
import com.kt.onnuipay.kafka.kafkanetty.kafka.model.enums.TypeOfSending;

public class MsgFromKafkaAndroid {

	public static MsgFromKafkaVo voForMultiplePushWithValidDataBody = TestUtil.createMsgVo(KafkaKeyEnum.ANDROID , DataBodys.bodyOfWithValidHeaderAndBody, MsgType.APP_PUSH, TypeOfSending.MULTIPLE, Options.targets);
	
	public static MsgFromKafkaVo voForSinglePushWithInvalidHeader =  TestUtil.createMsgVoForAndroid(DataBodys.bodyOfInvalidHeaderAndValidBody, TypeOfSending.SINGLE, Options.targets);
	
	public static MsgFromKafkaVo voForAndroidWithInvalidHeaderAndInvalidBody = TestUtil.createMsgVoForAndroid(DataBodys.bodyOfInvalidHeaderAndInValidBody, TypeOfSending.SINGLE, Options.targets ) ;
	
	public static MsgFromKafkaVo voForSinglePushWithValidDataBody = TestUtil.createMsgVoForAndroid( DataBodys.bodyOfWithValidHeaderAndBody, TypeOfSending.SINGLE, Options.targets );
}
//strong